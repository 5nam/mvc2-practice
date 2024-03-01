package hello.login.web.member;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    /**
     * @ModelAttribute("member")Member member
     * == @ModelAttribute Member member
     * 원래 클래스 명에서 첫 번째만 소문자로 바꿔서 모델에 담김
     * 아래 굳이 적은 이유는 타임리프로 넘어갈 때 가끔 인식이 안되어서
     * @param member
     * @return
     */
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "/members/addMemberForm";
    }

    /**
     * @param member -> ModelAttribute 로 받을 때, Member 객체로 받는게 아니라 Form 을 따로 생성해서 받는 것이 더 좋음(DTO)
     * @param bindingResult
     * @return
     */
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        memberRepository.save(member);

        return "redirect:/";
    }
}
