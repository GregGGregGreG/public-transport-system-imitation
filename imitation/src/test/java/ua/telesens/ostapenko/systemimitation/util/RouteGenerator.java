package ua.telesens.ostapenko.systemimitation.util;

import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.telesens.ostapenko.systemimitation.model.internal.DayType.HOLIDAY;
import static ua.telesens.ostapenko.systemimitation.model.internal.DayType.WORKDAY;

/**
 * @author root
 * @since 10.01.16
 */
public class RouteGenerator {

    public static RouteList get() {
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
        Map<DayType, PassengerGenerationRuleList> rulesGagarina = new HashMap<>();
        rulesGagarina.put(WORKDAY, PassengerGenerationRuleList.of(workDayRulesOfGagarina));
        rulesGagarina.put(HOLIDAY, PassengerGenerationRuleList.of(holidayRulesOfGagarina));
        Station gagarina = Station.of("Gagarina", rulesGagarina);

        /////////-------------------------------
        List<PassengerGenerationRule> workDayRulesOfNovoselevka = new ArrayList<>();
        workDayRulesOfNovoselevka.add(PassengerGenerationRule.of(
                9, LocalTime.of(6, 0), LocalTime.of(9, 0), LocalTime.of(0, 13))
        );
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
        Map<DayType, PassengerGenerationRuleList> rulesNovoselevka = new HashMap<>();
        rulesNovoselevka.put(WORKDAY, PassengerGenerationRuleList.of(workDayRulesOfNovoselevka));
        rulesNovoselevka.put(HOLIDAY, PassengerGenerationRuleList.of(holidayRulesOfNovoselevka));
        Station novoselevka = Station.of("Novoselevka", rulesNovoselevka);

        /////////-------------------------------
        List<PassengerGenerationRule> workDayRulesOfDerchprom = new ArrayList<>();
        workDayRulesOfDerchprom.add(PassengerGenerationRule.of(
                9, LocalTime.of(6, 0), LocalTime.of(9, 0), LocalTime.of(0, 13))
        );
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
        Map<DayType, PassengerGenerationRuleList> rulesDerchprom = new HashMap<>();
        rulesDerchprom.put(WORKDAY, PassengerGenerationRuleList.of(workDayRulesOfDerchprom));
        rulesDerchprom.put(HOLIDAY, PassengerGenerationRuleList.of(holidayRulesOfDerchprom));
        Station derchprom = Station.of("Derchprom", rulesDerchprom);

        ////-------------------------------------------- Create Arc list>>>>>>
        List<RouteArc> arcs = new ArrayList<>();
        arcs.add(RouteArc.of(gagarina, novoselevka, LocalTime.of(0, 18)));
        arcs.add(RouteArc.of(novoselevka, derchprom, LocalTime.of(0, 5)));
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

        Map<DayType, RouteTrafficRuleList> trafficRules = new HashMap<>();
        trafficRules.put(WORKDAY, RouteTrafficRuleList.of(workDayTrafficRules));
        trafficRules.put(HOLIDAY, RouteTrafficRuleList.of(holiDayTrafficRules));

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

        return RouteList.of(routes);
    }

}
