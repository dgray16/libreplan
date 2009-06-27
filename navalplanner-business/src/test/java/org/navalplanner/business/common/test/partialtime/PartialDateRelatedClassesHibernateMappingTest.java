package org.navalplanner.business.common.test.partialtime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.navalplanner.business.BusinessGlobalNames.BUSINESS_SPRING_CONFIG_FILE;
import static org.navalplanner.business.test.BusinessGlobalNames.BUSINESS_SPRING_CONFIG_TEST_FILE;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.navalplanner.business.common.partialtime.IntervalOfPartialDates;
import org.navalplanner.business.common.partialtime.PartialDate;
import org.navalplanner.business.common.partialtime.TimeQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { BUSINESS_SPRING_CONFIG_FILE,
        BUSINESS_SPRING_CONFIG_TEST_FILE })
@Transactional
public class PartialDateRelatedClassesHibernateMappingTest {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Test
    public void partialDatesCanBeSavedAndRetrieved() {
        EntityContainingPartialDate entity = new EntityContainingPartialDate();
        PartialDate partialDate = PartialDate.createFrom(new LocalDate(2000, 4,
                20));
        entity.setPartialDate(partialDate);
        getSession().save(entity);
        getSession().flush();
        getSession().evict(entity);
        EntityContainingPartialDate reloaded = (EntityContainingPartialDate) getSession()
                .get(EntityContainingPartialDate.class, entity.getId());
        assertThat(reloaded.getPartialDate(), equalTo(partialDate));
    }

    @Test
    public void intervalsCanBeSavedAndRetrieved() {
        EntityContainingIntervalOfPartialDates entity = new EntityContainingIntervalOfPartialDates();
        PartialDate start = PartialDate.createFrom(new LocalDate(2000, 4, 20));
        PartialDate end = PartialDate.createFrom(new LocalDate(2001, 4, 23));
        IntervalOfPartialDates original = new IntervalOfPartialDates(start, end);
        entity.setInterval(original);
        getSession().save(entity);
        getSession().flush();
        getSession().evict(entity);
        EntityContainingIntervalOfPartialDates reloaded = (EntityContainingIntervalOfPartialDates) getSession()
                .get(
                EntityContainingIntervalOfPartialDates.class, entity.getId());
        assertThat(reloaded.getInterval(), equalTo(original));
    }

    @Test
    public void timeQuantitysCanBeSavedAndRetrieved() {
        EntityContainingTimeQuantity entity = new EntityContainingTimeQuantity();
        TimeQuantity duration = new IntervalOfPartialDates(PartialDate.createFrom(new LocalDate(2000, 4, 20)), PartialDate.createFrom(new LocalDate(2001, 4, 23)))
                .getDuration();
        entity.setTimeQuantity(duration);
        getSession().save(entity);
        getSession().flush();
        getSession().evict(entity);
        EntityContainingTimeQuantity reloaded = (EntityContainingTimeQuantity) getSession()
                .get(
                EntityContainingTimeQuantity.class, entity.getId());
        TimeQuantity timeQuantity = reloaded.getTimeQuantity();
        assertThat(timeQuantity, equalTo(duration));
    }
}
