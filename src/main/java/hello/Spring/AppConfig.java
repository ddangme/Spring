package hello.Spring;

import hello.Spring.discount.FixDiscountPolicy;
import hello.Spring.member.MemberService;
import hello.Spring.member.MemberServiceImpl;
import hello.Spring.member.MemoryMemberRepository;
import hello.Spring.order.OrderService;
import hello.Spring.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

    



}
