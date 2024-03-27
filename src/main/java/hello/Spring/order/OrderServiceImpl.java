package hello.Spring.order;

import hello.Spring.discount.DiscountPolicy;
import hello.Spring.discount.FixDiscountPolicy;
import hello.Spring.member.Member;
import hello.Spring.member.MemberRepository;
import hello.Spring.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
