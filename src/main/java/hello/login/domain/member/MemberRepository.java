package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    /**
     * 메모리에 저장하는 것
     * 원래대로라면 인터페이스로 만드는 것이 더 좋음
     * 구현체로 DB 로직을 다양하게 변경 가능
     */
    private static Map<Long, Member> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; // static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    /**
     * Map 이므로 id 넣으면 값 바로 나옴
     * ID(DB ID) 로 멤버 조회하기
     * @param id
     * @return Member
     */
    public Member findById(Long id) {
        return store.get(id);
    }

    /**
     * 로그인 ID 로 멤버 조회하기
     * 못 찾을 수 있으니, Optional 로 반환
     * 로직
     * - findAll 로 조회해서 리스트를 하나씩 조회하면서, 매개변수로 넘어온 loginId 와 일치하는 것이 있으면
     * - Optional.of(m) return 하고, 끝까지 못 찾으면 return Optional.empty()
     */
    public Optional<Member> findByLoginId(String loginId) {

        /*
        List<Member> all = findAll();
        for (Member m : all) {
            if(m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
         */

        return findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId)) // 이 조건에 만족하는 member 만 다음 단계(findFirst)로 넘어가는 것
                .findFirst();
    }

    /**
     * 멤버 전체 조회
     * .values 로 ArrayList 를 생성 : List 로 변환한 것
     */
    public List<Member> findAll() {
        log.info("members : {}", store.values()); // 어떻게 반환되는지 궁금해서 추가
        return new ArrayList<>(store.values());
    }

    /**
     * 테스트할 때 초기화하기 위한 것
     */
    public void clearStore() {
        store.clear();
    }

}
