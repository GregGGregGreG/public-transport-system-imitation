package ua.telesens.ostapenko.systemimitation.util;

import org.junit.Before;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ua.telesens.ostapenko.systemimitation.model.internal.DayType.HOLIDAY;
import static ua.telesens.ostapenko.systemimitation.model.internal.DayType.WORKDAY;

/**
 * @author root
 * @since 10.01.16
 */
public class ImitationSourceGenerator {

    @Before
    public void setUp() throws Exception {


    }

    public static ImitationSource get() {
        List<PassengerGenerationRule> workDayRulesOfGagarina = new ArrayList<>();
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                8, LocalTime.of(6, 0), LocalTime.of(9, 0), LocalTime.of(0, 15))
        );
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                4, LocalTime.of(9, 0), LocalTime.of(13, 0), LocalTime.of(0, 17))
        );
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                2, LocalTime.of(13, 0), LocalTime.of(16, 0), LocalTime.of(0, 17))
        );
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                8, LocalTime.of(16, 0), LocalTime.of(19, 0), LocalTime.of(0, 15))
        );
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                4, LocalTime.of(19, 0), LocalTime.of(23, 0), LocalTime.of(0, 15))
        );

        List<PassengerGenerationRule> holidayRulesOfGagarina = new ArrayList<>();
        holidayRulesOfGagarina.add(PassengerGenerationRule.of(
                4, LocalTime.of(6, 0), LocalTime.of(22, 17), LocalTime.of(0, 16))
        );
        List<PassengerGenerationRuleList> rulesGagarina = new ArrayList<>();
        rulesGagarina.add(PassengerGenerationRuleList.of(WORKDAY, workDayRulesOfGagarina));
        rulesGagarina.add(PassengerGenerationRuleList.of(HOLIDAY, holidayRulesOfGagarina));
        Station gagarina = Station.of("Gagarina", rulesGagarina);

        /////////-------------------------------
        List<PassengerGenerationRule> workDayRulesOfNovoselevka = new ArrayList<>();
        workDayRulesOfNovoselevka.add(PassengerGenerationRule.of(
                9, LocalTime.of(6, 0), LocalTime.of(9, 0), LocalTime.of(0, 13))
        );
        //!!!!!!!!!
//        workDayRulesOfNovoselevka.add(PassengerGenerationRule.of(
//                9, LocalTime.of(6, 0), LocalTime.of(9, 0), LocalTime.of(0, 13))
//        );

        workDayRulesOfNovoselevka.add(PassengerGenerationRule.of(
                4, LocalTime.of(9, 0), LocalTime.of(13, 0), LocalTime.of(0, 17))
        );
        workDayRulesOfNovoselevka.add(PassengerGenerationRule.of(
                1, LocalTime.of(13, 0), LocalTime.of(16, 0), LocalTime.of(0, 17))
        );
        workDayRulesOfNovoselevka.add(PassengerGenerationRule.of(
                9, LocalTime.of(16, 0), LocalTime.of(19, 0), LocalTime.of(0, 15))
        );
        workDayRulesOfNovoselevka.add(PassengerGenerationRule.of(
                3, LocalTime.of(19, 0), LocalTime.of(22, 0), LocalTime.of(0, 15))
        );

        List<PassengerGenerationRule> holidayRulesOfNovoselevka = new ArrayList<>();
        holidayRulesOfNovoselevka.add(PassengerGenerationRule.of(
                3, LocalTime.of(6, 0), LocalTime.of(22, 0), LocalTime.of(0, 15))
        );
        List<PassengerGenerationRuleList> rulesNovoselevka = new ArrayList<>();
        rulesNovoselevka.add(PassengerGenerationRuleList.of(WORKDAY, workDayRulesOfNovoselevka));
        rulesNovoselevka.add(PassengerGenerationRuleList.of(HOLIDAY, holidayRulesOfNovoselevka));
        Station novoselevka = Station.of("Novoselevka", rulesNovoselevka);

        /////////-------------------------------

//        !!!!!!!!!!!!!
        List<PassengerGenerationRule> workDayRulesOfDerchprom = new ArrayList<>();
        workDayRulesOfDerchprom.add(PassengerGenerationRule.of(
                9, LocalTime.of(6, 0), LocalTime.of(9, 0), LocalTime.of(0, 13))
        );
//        workDayRulesOfDerchprom.add(PassengerGenerationRule.of(
//                9, LocalTime.of(7, 0), LocalTime.of(9, 0), LocalTime.of(0, 13))
//        );
        workDayRulesOfDerchprom.add(PassengerGenerationRule.of(
                4, LocalTime.of(9, 0), LocalTime.of(13, 0), LocalTime.of(0, 17))
        );
        workDayRulesOfDerchprom.add(PassengerGenerationRule.of(
                1, LocalTime.of(13, 0), LocalTime.of(16, 0), LocalTime.of(0, 17))
        );
        workDayRulesOfDerchprom.add(PassengerGenerationRule.of(
                9, LocalTime.of(16, 0), LocalTime.of(19, 0), LocalTime.of(0, 15))
        );
        workDayRulesOfDerchprom.add(PassengerGenerationRule.of(
                3, LocalTime.of(19, 0), LocalTime.of(22, 0), LocalTime.of(0, 15))
        );

        List<PassengerGenerationRule> holidayRulesOfDerchprom = new ArrayList<>();
        holidayRulesOfDerchprom.add(PassengerGenerationRule.of(
                3, LocalTime.of(6, 0), LocalTime.of(22, 0), LocalTime.of(0, 15))
        );
        List<PassengerGenerationRuleList> rulesDerchprom = new ArrayList<>();
        rulesDerchprom.add(PassengerGenerationRuleList.of(WORKDAY, workDayRulesOfDerchprom));
        rulesDerchprom.add(PassengerGenerationRuleList.of(HOLIDAY, holidayRulesOfDerchprom));
        Station derchprom = Station.of("Derchprom", rulesDerchprom);

        ////-------------------------------------------- Create Arc List>>>>>>
        List<RouteArc> arcs = new ArrayList<>();
        arcs.add(RouteArc.of(1, gagarina, novoselevka, LocalTime.of(0, 18)));
        arcs.add(RouteArc.of(2, novoselevka, derchprom, LocalTime.of(0, 5)));
//        !!!!!!!!
//        arcs.add(RouteArc.of(3, novoselevka, derchprom, LocalTime.of(0, 5)));
        ///------------------------------------------  Create Bus List >>>>>>
        Bus busOne = Bus.of(832, 45);
        Bus busTwo = Bus.of(52, 45);
        Bus busThre = Bus.of(2668, 45);
        List<Bus> buses = new ArrayList<>();
        buses.add(busOne);
        buses.add(busTwo);
        buses.add(busThre);

        ////----------------------  WorkDay traffic rule>
        List<RouteTrafficRule> workDayTrafficRules = new ArrayList<>();
        workDayTrafficRules.add(RouteTrafficRule.of(3, 7, LocalTime.of(0, 15), LocalTime.of(0, 30)));
        workDayTrafficRules.add(RouteTrafficRule.of(2, 3, LocalTime.of(0, 20), LocalTime.of(0, 38)));
        workDayTrafficRules.add(RouteTrafficRule.of(3, 7, LocalTime.of(0, 15), LocalTime.of(0, 30)));

        ////---------------------- Holiday Traffic rule >
        List<RouteTrafficRule> holiDayTrafficRules = new ArrayList<>();
        holiDayTrafficRules.add(RouteTrafficRule.of(2, 5, LocalTime.of(0, 15), LocalTime.of(0, 40)));
        holiDayTrafficRules.add(RouteTrafficRule.of(1, 3, LocalTime.of(0, 18), LocalTime.of(0, 45)));
        holiDayTrafficRules.add(RouteTrafficRule.of(2, 5, LocalTime.of(0, 15), LocalTime.of(0, 40)));

        List<RouteTrafficRuleList> trafficRules = new ArrayList<>();
        trafficRules.add(RouteTrafficRuleList.of(WORKDAY, workDayTrafficRules));
        trafficRules.add(RouteTrafficRuleList.of(HOLIDAY, holiDayTrafficRules));

        Route route = Route.of(
                "232",
                RouteType.CYCLE,
                5.75,
                arcs,
                buses,
                LocalTime.of(6, 0),
                trafficRules);
        List<Route> routes = new ArrayList<>();
        routes.add(route);

        RouteList routeList = RouteList.of(routes);
        LocalDateTime starting = LocalDateTime.of(2015, Month.NOVEMBER, 12, 6, 0);
        LocalDateTime end = LocalDateTime.of(2015, Month.NOVEMBER, 15, 23, 50);
        int minTimePassengerWaiting = 15;
        // use from calculation end day
        int maxTimePassengerWaiting = 120;
        return ImitationSource.of(starting, end, routeList, minTimePassengerWaiting, maxTimePassengerWaiting);
    }

}
