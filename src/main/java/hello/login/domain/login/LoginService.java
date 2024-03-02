package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     *
     * @param loginId
     * @param password
     * @return null 이면 로그인 실패
     */
    public Member login(String loginId, String password) {

        /* stream 적용 X
        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get();

        if(member.getPassword().equals(password)) {
            return member;
        }
        return null;
         */

        // member 의 password 와 일치하면 해당 member 객체 반환, 아니면 null 반환
        return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
