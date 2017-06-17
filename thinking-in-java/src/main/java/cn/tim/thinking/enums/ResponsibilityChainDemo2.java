package cn.tim.thinking.enums;

import java.util.EnumMap;
import java.util.Iterator;

/**
 * Created by LuoLiBing on 16/12/12.
 * 责任链
 * 以多种不同的方式来解决同一个问题, 然后将这些方式链接在一起. 当一个请求到来时, 它遍历这个链, 直到链中某个解决方案能够处理该请求为止. 不能处理也可以视为一种处理方式
 *
 */
public class ResponsibilityChainDemo2 {

    // mail的属性都是enum类型
    static class Mail {
        enum GeneralDelivery {
            YES, NO1, NO2, NO3, NO4, NO5
        }

        enum Scannability {
            UNSCANNABLE, YES1, YES2, YES3, YES4
        }

        enum Readability {
            ILLEGIBLE, YES1, YES2, YES3, YES4
        }

        enum Address {
            INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6
        }

        enum ReturnAddress {
            MISSING, OK1, OK2, OK3, OK4, OK5
        }

        enum Forward {
            YES, NO
        }

        GeneralDelivery generalDelivery;

        Scannability scannability;

        Readability readability;

        Address address;

        ReturnAddress returnAddress;

        Forward forward;

        static long counter = 0;

        long id = counter++;

        @Override
        public String toString() {
            return "Mail " + id;
        }

        public String detail() {
            return "General delivery: " + generalDelivery +
                    ", Scan ability: " + scannability +
                    ", read ability: " + readability +
                    ", address: " + address +
                    ", return address" + returnAddress;
        }

        // 随机测试邮件
        public static Mail randomMail() {
            Mail mail = new Mail();
            mail.generalDelivery = RandomEnumDemo1.Enums.random(GeneralDelivery.class);
            mail.scannability = RandomEnumDemo1.Enums.random(Scannability.class);
            mail.readability = RandomEnumDemo1.Enums.random(Readability.class);
            mail.address = RandomEnumDemo1.Enums.random(Address.class);
            mail.returnAddress = RandomEnumDemo1.Enums.random(ReturnAddress.class);
            mail.forward = RandomEnumDemo1.Enums.random(Forward.class);
            return mail;
        }

        // 随机测试邮件集合
        public static Iterable<Mail> generator(final int count) {
            return new Iterable<Mail> () {
                int n = count;

                @Override
                public Iterator<Mail> iterator() {
                    return new Iterator<Mail>() {
                        @Override
                        public boolean hasNext() {
                            return n -- > 0;
                        }

                        @Override
                        public Mail next() {
                            return randomMail();
                        }
                    };
                }
            };
        }
    }


    interface Command {
        boolean handle(Mail m);
    }

    // 邮件发送, 下面的实现方式类似于命令设计模式
    static class PostOffice {

        static EnumMap<MailHandler, Command> em;

        static {
            em = new EnumMap<>(MailHandler.class);
            em.put(MailHandler.GENERAL_DELIVERY, (mail) -> {
                switch (mail.generalDelivery) {
                    case YES:
                        System.out.println("Using general delivery for " + mail);
                        return true;
                    default: return false;
                }
            });
            em.put(MailHandler.MACHINE_SCAN, (m) -> {
                switch (m.scannability) {
                    case UNSCANNABLE: return false;
                    default:
                        switch (m.address) {
                            case INCORRECT: return false;
                            default:
                                System.out.println("Delivering " + m + " automatically");
                                return true;
                        }
                }
            });
            em.put(MailHandler.VISUAL_INSPECTION, (m) -> {
                switch (m.readability) {
                    case ILLEGIBLE: return false;
                    default:
                        switch (m.address) {
                            case INCORRECT: return false;
                            default:
                                System.out.println("Delivering " + m + " normally");
                                return true;
                        }
                }
            });
            em.put(MailHandler.RETURN_TO_SENDER, (m) -> {
                switch (m.returnAddress) {
                    case MISSING: return false;
                    default:
                        System.out.println("Returning " + m + " to sender");
                        return true;
                }
            });
            em.put(MailHandler.FORWARD, (m) -> {
                switch (m.forward) {
                    // 允许转发, 才转发
                    case YES:
                        System.out.println("forward " + m + " to sender");
                        return true;
                    default:
                        System.out.println("Forbidden to reward");
                        return false;
                }
            });
        }

        // 邮件处理, 判断对应的结点能否处理, 能处理则处理返回true. 责任链的顺序就是枚举定义的顺序,所以这个地方顺序很重要
        enum MailHandler {

            // 处理方式1
            GENERAL_DELIVERY,

            // 处理方式2
            MACHINE_SCAN,

            // 处理方式3
            VISUAL_INSPECTION,

            // 处理方式4
            RETURN_TO_SENDER,

            // 处理方式5: 转发
            FORWARD
        }

        // 邮件处理
        static void handle(Mail m) {
            for(Command handler : em.values()) {
                // 只要符合这个要求,就可以发送
                if(handler.handle(m)) {
                    return;
                }
            }
            // 如果所有要求都不符合,则判定为死邮件
            System.out.println(m + " is a dead letter");
        }

        public static void main(String[] args) {
            Iterable<Mail> mails = Mail.generator(100);
            for(Mail mail : mails) {
                System.out.println(mail.detail());
                handle(mail);
                System.out.println("*****");
            }
        }
    }

}
