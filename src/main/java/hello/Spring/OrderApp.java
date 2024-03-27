package hello.Spring;

import hello.Spring.member.Grade;
import hello.Spring.member.Member;
import hello.Spring.member.MemberService;
import hello.Spring.member.MemberServiceImpl;
import hello.Spring.order.Order;
import hello.Spring.order.OrderService;
import hello.Spring.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10_000);
        System.out.println("order = " + order);
        System.out.println("order.calculatePrice = " + order.calculatePrice());

    }
}
