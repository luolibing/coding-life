package cn.tim.spring4.aware;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

import javax.management.Notification;

/**
 * Created by LuoLiBing on 16/6/22.
 */
@Component
public class NotificationPublisherAwareTest implements NotificationPublisherAware {

    private NotificationPublisher notificationPublisher;

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    public void publishJmx() {
        notificationPublisher.sendNotification(new Notification("add", "lyo", 1));
    }
}
