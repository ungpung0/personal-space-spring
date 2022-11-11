package com.study.clubproject.security.service;

import com.study.clubproject.entity.ClubMember;
import com.study.clubproject.entity.ClubMemberRole;
import com.study.clubproject.repository.ClubMemberRepository;
import com.study.clubproject.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {
    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> result = clubMemberRepository.findByEmail(email, true);

        if(result.isPresent())
            return result.get();

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .formSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        clubMemberRepository.save(clubMember);

        return clubMember;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("User Request : " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("Client Name : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + " : " + v);
        });

        String email = null;
        if(clientName.equals("Google"))
            email = oAuth2User.getAttribute("email");

        log.info("Email : " + email);

        ClubMember clubMember = saveSocialMember(email);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                true,
                clubMember.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name())
                ).collect(Collectors.toSet()),
                oAuth2User.getAttributes()
        );

        clubAuthMemberDTO.setName(clubMember.getName());

        return clubAuthMemberDTO;
    }
}
