package _1.vietpq.job_hunter.service.subscriber;

import _1.vietpq.job_hunter.domain.Subscriber;

public interface SubscriberService {

    boolean isEmail(String email);
    Subscriber handleCreateSubscriber(Subscriber subscriber);
    Subscriber handleUpdateSubscriber(Subscriber subscriberDb,Subscriber subscriber);

    Subscriber fetchSubscriberById(long id);
    Subscriber getSubscriberByEmail(String email);
    public void sendSubscriberEmailJobs();

}
