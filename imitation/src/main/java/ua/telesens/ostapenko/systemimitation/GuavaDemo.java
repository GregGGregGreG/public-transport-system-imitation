package ua.telesens.ostapenko.systemimitation;

import com.google.common.eventbus.*;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author root
 * @since 07.12.15
 */
public class GuavaDemo {
}

/**
 * @author andrus.god
 * @since 05.12.2015.
 */

 class Runner {
    @ToString
    public static abstract class PurchaseEvent {
        String item;

        public PurchaseEvent(String item) {
            this.item = item;
        }
    }

    @ToString(callSuper = true)
    public static class CashPurchaseEvent extends PurchaseEvent {
        int amount;

        public CashPurchaseEvent(String item, int amount) {
            super(item);
            this.amount = amount;
        }
    }

    @ToString(callSuper = true)
    public static class CreditPurchaseEvent extends PurchaseEvent {
        int amount;
        String cardNumber;

        public CreditPurchaseEvent(String item, int amount, String cardNumber) {
            super(item);
            this.amount = amount;
            this.cardNumber = cardNumber;
        }
    }

    @ToString
    public static class MultiHandlerSubscriber {
        List<CashPurchaseEvent> cashEvents = new ArrayList<>();
        List<CreditPurchaseEvent> creditEvents = new ArrayList<>();

        public MultiHandlerSubscriber(EventBus eventBus) {
            eventBus.register(this);
        }

        @Subscribe
        @AllowConcurrentEvents
        public void handleCashEvents(CashPurchaseEvent event) {
            cashEvents.add(event);
        }

        @Subscribe
        @AllowConcurrentEvents
        public void handleCreditEvents(CreditPurchaseEvent event) {
            creditEvents.add(event);
        }
    }

    public static class PurchaseCanceled implements SubscriberExceptionHandler {
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            System.err.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        EventBus eventBus = new AsyncEventBus(ForkJoinPool.commonPool(), new PurchaseCanceled());
        MultiHandlerSubscriber multiHandlerSubscriber = new MultiHandlerSubscriber(eventBus);

        eventBus.post(new CashPurchaseEvent("test1", 100));
        eventBus.post(new CreditPurchaseEvent("test2", 100, "1245524"));
        eventBus.post(new CashPurchaseEvent("test3", 200));

        System.out.println(multiHandlerSubscriber);
    }
}
