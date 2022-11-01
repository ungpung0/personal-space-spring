package com.study.clubproject.security.service;

import com.study.clubproject.entity.ClubMember;
import com.study.clubproject.repository.ClubMemberRepository;
import com.study.clubproject.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUserName " + username);

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);

        if(result.isPresent())
            throw new UsernameNotFoundException("Check Email or Social");

        ClubMember clubMember = result.get();

        log.info("=========================");
        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFormSocial(),
                clubMember.getRoleSet().stream().map(clubMemberRole ->
                        new SimpleGrantedAuthority("ROLE_" + clubMemberRole.name())).collect(Collectors.toSet())
        );

        clubAuthMember.setName(clubMember.getName());
        clubAuthMember.setFromSocial(clubMember.isFormSocial());

        return clubAuthMember;
    }
}
