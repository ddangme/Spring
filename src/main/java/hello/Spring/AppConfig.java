package hello.Spring;

import hello.Spring.discount.DiscountPolicy;
import hello.Spring.discount.FixDiscountPolicy;
import hello.Spring.discount.RateDiscountPolicy;
import hello.Spring.member.MemberService;
import hello.Spring.member.MemberServiceImpl;
import hello.Spring.member.MemoryMemberRepository;
import hello.Spring.order.OrderService;
import hello.Spring.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private static MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    private DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }


}
