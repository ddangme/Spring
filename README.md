# Spring
Spring의 기본 원리 학습을 위한 프로젝트


## 비즈니스 요구사항과 설계
- 회원
  - 회원은 가입하고 조회할 수 있다.
  - 회원은 일반과 VIP 두 가지 등급이 있다.
  - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)
- 주문과 할인 정책
  - 회원은 상품을 주문할 수 있다.
  - 회원 등급에 따라 할인 정책을 적용할 수 있다.
  - 할인 정책은 모든 VIP는 1,000원을 할인해주는 고정 금액 할인을 적용한다. (추후 변경 가능)
  - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다.
최악의 경우 할인을 적용하지 않을 수도 있다. (미확정)

    
🔥회원 데이터, 할인 정책과 같은 부분은 지금 결정하기 어려운 부분 상황이라고 가정하고, 객체 지향 설계 방법으로 현재 상태에서 개발을 진행한다.
- 인터페이스를 만들고 구현체를 언제든지 바꿀 수 있도록 설계한다.

##  회원 도메인 설계
![img.png](img/회원%20도메인%20협력%20관계.png)

![img.png](img/회원%20클래스%20다이어그램.png)

![img.png](img/회원%20객체%20다이어그램.png)

- 회원 도메인 설계의 문제점
  - 다른 저장소로 변경할 때 OCP 원칙을 준수하고 있나?
  - DIP를 잘 지키고 있나?
  - **의존 관계가 인터페이스 뿐만 아니라 구현까지 모두 의존하는 문제점이 있다.**


## 주문과 할인 도메인 설계
![img.png](img/주문%20도메인%20협력,%20역할,%20책임.png)
1. 주문 생성: 클라이언트는 주문 서비스에 주문 생성을 요청한다.
2. 회원 조회: 할인을 위해서 회원 등급이 필요하기 때문에, 주문 서비스는 회원 저장소에서 회원을 조회한다.
3. 할인 적용: 주문 서비스는 회원 등급에 따른 할인 여부를 할인 정책에 위임한다.
4. 주문 결과 반환: 주문 서비스는 할인 결과를 포함한 주문 결과를 반환한다.

![img.png](img/주문%20도메인%20전체.png)
- 역할과 구분을 분리해서 자유롭게 구현 객체를 조립할 수 있도록 설계되었다.
따라서 회원 저장소와 할인 정책도 유연하게 변경할 수 있다.

![img.png](img/주문%20도메인%20클래스%20다이어그램.png)

![img.png](img/주문%20도메인%20객체%20다이어그램1.png)
- 회원을 메모리에서 조회하고, 정액 할인 정책(고정 금액)을 지원해도 주문 서비스를 변경하지 않아도 된다.
역할들의 협력관계를 그대로 재사용 할 수 있다.

![img.png](img/주문%20도메인%20객체%20다이어그램2.png)
- 회원을 메모리가 아닌 실제 DB에서 조회하고, 정률 할인 정책(퍼센트)을 지원해도 주문 서비스를 변경하지 않아도 된다.
협력 관계를 그대로 재사용 할 수 있다.


### 새로운 할인 정책 개발
- 서비스 오픈 직전에, 할인 정책을 정액 할인이 아닌, 정률할인으로 변경한다.
  ![img.png](img/RateDiscountPolicy%20추가.png)


### 새로운 할인 정책 적용과 문제점
- 할인 정책을 변경하기 위해선, ```OrderServiceImpl``` 코드를 수정해야 한다.
  ```java
  public class OrderServiceImpl implements OrderService {
        // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
        private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
        ...
  }
  ```
  
- 문제점
  - [x] 역할과 구현을 분리했다.
  - [x] 다형성을 활용했다.
  - [x] 인터페이스와 구현 객체를 분리했다.
  - [ ] OCP, DIP 같은 객체지향 설계 원칙을 준수했다.
    - 클래스 의존 관계를 분석해보면, 추상(인터페이스)뿐만 아니라, **구체(구현) 클래스에도 의존**하고 있다.
      - 추상(인터페이스) 의존 : DiscountPolicy
      - 구체(구현) 클래스 : FixDiscountPolicy, RateDiscountPolicy
    - 지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다. 따라서 OCP를 위반한다.
    - 기대했던 의존관계
      ![img.png](img/기대했던%20의존관계.png)
    - 실제 의존관계
      ![img.png](img/실제%20의존관계.png)

- 문제 해결 방법
  - 클라이언트 코드인 OrderServiceImpl은 DiscoutPolicy의 인터페이스 뿐만 아니라 구체 클래스도 함께 의존한다.
  - 그래서 구체 클래스를 변경할 때 클라이언트 코드도 함께 변경해야 한다.
  - DIP 위반 -> 추상에만 의존하도록 변경(인터페이스에만 의존)
  - DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경하면 된다.
  - 🔥인터페이스에만 의존하도록 설계를 변경해보자.
    ![img.png](img/변경된%20설계.png)
    ```java
    private DiscountPolicy discountPolicy;
    ```
    - 하지만, 이런 경우 구현체가 없기 때문에 NPE(Null Pointer Exception)이 발생한다.
    - 이 문제를 해결하려면 누군가 클라이언트인 ```OrderServiceImpl```에 ```DiscountPolicy```의 구현 객체를 대신 생성하고 주입해주어야 한다.

### 관심사의 분리
- 인터페이스는 자신의 역할에만 집중해야 한다.
- 코드를 수정하더라도 다른 코드에 영향을 주면 안된다.


### AppConfig의 등장
- 애플리케이션의 전체 동작 방식을 구성(config)하기 위해, 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스를 만들자.
```java
public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
```
- AppConfig은 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다.
  - MemberServiceImpl
  - MemoryMemberRepository
  - OrderServiceImpl
  - FixDiscountPolicy
- AppConfig은 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해 주입(연결)해준다.
  - MemberServiceImpl -> MemoryMemberRepository
  - OrderServiceImpl -> MemoryMemberRepository, FixDiscountPolicy
  ![img.png](img/변경한%20클래스%20다이어그램.png)
- 객체의 생성과 연결은 ```AppConfig```가 담당한다.
- DIP완성: ```MemberServiceImpl```은 ```MemberRepository```인 추상에만 의존하면 된다. 이제 구체 클래스를 몰라도 된다.
- 관심사의 분리: 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
  ![img.png](img/변경된%20회원%20객체%20인스턴스%20다이어그램.png)
  - ```appConfig```객체는 ```memoryMemberRepository```객체를 생성하고 그 참조값을 ```memberServiceImpl```을 생성하면서 생성자로 전달한다.
  - 클라이언트인 ```memberServiceImpl``` 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것과 같다고 해서 DI(Dependency Injection) 우리말로 의존 관계 주입 또는 위존성 주입이라고 한다.
- 정리
  - ```AppConfig```을 통해 관심사를 확실하게 분리한다.
  - ```AppConfig```은 공연의 기획자라고 생각한다.
  - ```AppConfig```은 구체 클래스를 선택한다. 애플리케이션이 어떻게 동작해야 할지 전체 구성을 책임진다.
  - 각각의 인터페이스는 담당하는 기능만 책임지면 된다.
  - ```OrderServiceImpl```은 기능을 실행하는 책임만 지면 된다.


### 새로운 구조와 할인 정책 적용
- AppConfig의 등장으로 애플리케이션이 크게 사용 영역과, 객체를 생성하고 구성(Configuration)하는 영역으로 분리되었다.
- 사용, 구성의 분리
  ![img.png](img/사용,%20구성의%20분리.png)
- 할인 정책의 변경
  ![img.png](img/할인%20정책의%20변경.png)
- ```FixDiscountPolicy``` -> ```RateDiscountPolicy```로 변경해도 구성 영역만 영향을 받고, 사용 영역은 전혀 영향을 받지 않는다.
- ```AppConfig```에서 할인 정책 역할을 담당하는 구현을 ```FixDiscountPolicy```에서 ```RateDiscountPolicy```객체로 변경한다.
- 이제 할인 정책을 변경해도, 애플리케이션의 구성 역할을 담당하는 AppConfig만 변경하면 된다.
- 클라이언트 코드인 ```OrderServiceImpl```를 포함해서 **사용 영역**의 어떤 코드도 변경할 필요가 없다.
- **구성 영역**은 당연히 변경된다.