package com.reprisk.companiesnews.filter;

import com.reprisk.companiesnews.BaseUnitTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WordFilterTest extends BaseUnitTest {

    private final WordFilter wordFilter = new WordFilter();
    private final static String company = "Apple";

    private static Stream<Arguments> filterShouldFilter() {
        return Stream.of(
                //Arguments.of(company + " l.t.d."),
                //Arguments.of(company + " l.t.d"),
                //Arguments.of(company + " ltd."),
                //Arguments.of(company + " ltd"),
                Arguments.of(company + " n.v."),
                Arguments.of(company + " n.v"),
                Arguments.of(company + " nv."),
                Arguments.of(company + " nv"),
                //Arguments.of(company + " i.n.c."),
                //Arguments.of(company + " i.n.c"),
                //Arguments.of(company + " inc."),
                //Arguments.of(company + " inc"),
                //Arguments.of(company + " c.o.r.p."),
                //Arguments.of(company + " c.o.r.p"),
                //Arguments.of(company + " corp."),
                //Arguments.of(company + " corp"),
                Arguments.of(company + " s.a."),
                Arguments.of(company + " s.a"),
                Arguments.of(company + " sa."),
                Arguments.of(company + " sa"),
                Arguments.of(company + " p.l.c."),
                Arguments.of(company + " p.l.c"),
                Arguments.of(company + " plc."),
                Arguments.of(company + " plc"),
                Arguments.of(company + " a.g."),
                Arguments.of(company + " a.g"),
                Arguments.of(company + " ag."),
                Arguments.of(company + " ag"),
                Arguments.of(company + " c.o."),
                Arguments.of(company + " c.o"),
                Arguments.of(company + " co."),
                Arguments.of(company + " co"),
                Arguments.of(company + " s.p.a."),
                Arguments.of(company + " s.p.a"),
                Arguments.of(company + " spa."),
                Arguments.of(company + " spa"),
                Arguments.of(company + " s.r.l."),
                Arguments.of(company + " srl."),
                Arguments.of(company + " s.r.l"),
                Arguments.of(company + " srl"),
                Arguments.of(company + " \""),
                Arguments.of(company + " &"),
                Arguments.of(company + " o.y.j."),
                Arguments.of(company + " o.y.j"),
                Arguments.of(company + " oyj."),
                Arguments.of(company + " oyj"),
                Arguments.of(company + " s.e."),
                Arguments.of(company + " s.e"),
                Arguments.of(company + " se."),
                Arguments.of(company + " se"),
                Arguments.of(company + " g.m.b.h."),
                Arguments.of(company + " g.m.b.h"),
                Arguments.of(company + " gmbh."),
                Arguments.of(company + " gmbh"),
                Arguments.of(company + " the"),
                Arguments.of(company + " p.t."),
                Arguments.of(company + " p.t"),
                Arguments.of(company + " pt."),
                Arguments.of(company + " pt"),
                Arguments.of(company + " a.s."),
                Arguments.of(company + " a.s"),
                Arguments.of(company + " as."),
                Arguments.of(company + " a/s"),
                Arguments.of(company + " as"),
                Arguments.of(company + " b.h.d."),
                Arguments.of(company + " b.h.d"),
                Arguments.of(company + " bhd."),
                Arguments.of(company + " bhd"),
                Arguments.of(company + " t.b.k."),
                Arguments.of(company + " t.b.k"),
                Arguments.of(company + " tbk."),
                Arguments.of(company + " tbk"),
                Arguments.of(company + " p.c.l."),
                Arguments.of(company + " p.c.l"),
                Arguments.of(company + " pcl."),
                Arguments.of(company + " pcl"),
                Arguments.of(company + " b.v."),
                Arguments.of(company + " b.v"),
                Arguments.of(company + " bv."),
                Arguments.of(company + " bv"),
                Arguments.of(company + " o.a.o."),
                Arguments.of(company + " o.a.o"),
                Arguments.of(company + " oao."),
                Arguments.of(company + " oao"),
                Arguments.of(company + " l.l.c."),
                Arguments.of(company + " l.l.c"),
                Arguments.of(company + " llc."),
                Arguments.of(company + " llc"),
                Arguments.of(company + " a.b."),
                Arguments.of(company + " a.b"),
                Arguments.of(company + " ab."),
                Arguments.of(company + " ab"),
                Arguments.of(company + " o.y."),
                Arguments.of(company + " o.y"),
                Arguments.of(company + " oy."),
                Arguments.of(company + " oy"),
                Arguments.of(company + " o.h.g."),
                Arguments.of(company + " o.h.g"),
                Arguments.of(company + " ohg."),
                Arguments.of(company + " ohg"),
                Arguments.of(company + " m.b.h."),
                Arguments.of(company + " m.b.h"),
                Arguments.of(company + " mbh."),
                Arguments.of(company + " mbh"),
                Arguments.of(company + " k.g."),
                Arguments.of(company + " k.g"),
                Arguments.of(company + " kg."),
                Arguments.of(company + " kg"),
                Arguments.of(company + " l.p."),
                Arguments.of(company + " lp."),
                Arguments.of(company + " l.p"),
                Arguments.of(company + " lp"),
                Arguments.of(company + " l.l.p."),
                Arguments.of(company + " l.l.p"),
                Arguments.of(company + " llp."),
                Arguments.of(company + " llp"),
                Arguments.of(company + " s.d.n."),
                Arguments.of(company + " s.d.n"),
                Arguments.of(company + " sdn."),
                Arguments.of(company + " sdn"),
                Arguments.of(company + " j.s.c."),
                Arguments.of(company + " j.s.c"),
                Arguments.of(company + " jsc."),
                Arguments.of(company + " jsc"),
                Arguments.of(company + " d.d."),
                Arguments.of(company + " d.d"),
                Arguments.of(company + " dd."),
                Arguments.of(company + " dd"),
                Arguments.of(company + " a.d."),
                Arguments.of(company + " a.d"),
                Arguments.of(company + " ad."),
                Arguments.of(company + " ad"),
                Arguments.of(company + " p.t.e."),
                Arguments.of(company + " p.t.e"),
                Arguments.of(company + " pte."),
                Arguments.of(company + " pte"),
                Arguments.of(company + " p.t.y."),
                Arguments.of(company + " p.t.y"),
                Arguments.of(company + " pty."),
                Arguments.of(company + " pty"),
                Arguments.of(company + " s.l."),
                Arguments.of(company + " s.l"),
                Arguments.of(company + " sl."),
                Arguments.of(company + " sl"),
                Arguments.of(company + " l.t.d.a."),
                Arguments.of(company + " l.t.d.a"),
                Arguments.of(company + " ltda."),
                Arguments.of(company + " ltda"),
                Arguments.of(company + " s.a.s."),
                Arguments.of(company + " s.a.s"),
                Arguments.of(company + " sas."),
                Arguments.of(company + " sas"),
                Arguments.of(company + " sicav"),
                Arguments.of(company + " s.a.b."),
                Arguments.of(company + " s.a.b"),
                Arguments.of(company + " sab."),
                Arguments.of(company + " sab")
        );
    }
    @ParameterizedTest
    @MethodSource
    void filterShouldFilter(String companyToFilter) {
        String filtered = wordFilter.filter(companyToFilter);

        assertEquals(company, filtered.trim());
    }

    private static Stream<Arguments> filterShouldNotFilter() {
        return Stream.of(
                Arguments.of(company + " 1"),
                Arguments.of(company + " 2"),
                Arguments.of(company + " 3")
        );
    }
    @ParameterizedTest
    @MethodSource
    void filterShouldNotFilter(String companyToFilter) {
        String filtered = wordFilter.filter(companyToFilter);

        assertEquals(companyToFilter, filtered);
    }

    private static Stream<Arguments> filterComplexWordsShouldFilter() {
        return Stream.of(
                Arguments.of("formerly known as " + company),
                Arguments.of("Formerly known as " + company),
                Arguments.of("formerly known " + company),
                Arguments.of("Formerly known " + company),
                Arguments.of("formerly " + company),
                Arguments.of("Formerly " + company),
                Arguments.of("also known as " + company),
                Arguments.of("Also known as " + company),
                Arguments.of("known as " + company),
                Arguments.of("Known as " + company),
                Arguments.of("also " + company),
                Arguments.of("Also " + company),
                Arguments.of("please refer to either " + company),
                Arguments.of("Please refer to either " + company),
                Arguments.of("please refer to " + company),
                Arguments.of("Please refer to " + company),
                Arguments.of("refer to " + company),
                Arguments.of("Refer to " + company),
                Arguments.of(company + " SAB de CV"),
                Arguments.of(company + " SA de CV"),
                Arguments.of(company + " de CV")
        );
    }
    @ParameterizedTest
    @MethodSource
    void filterComplexWordsShouldFilter(String companyToFilter) {
        String filtered = wordFilter.filterComplexWords(companyToFilter);

        assertEquals(company, filtered);
    }

    private static Stream<Arguments> filterComplexWordsShouldNotFilter() {
        return Stream.of(
                Arguments.of(company + " 1"),
                Arguments.of(company + " 2"),
                Arguments.of(company + " 3")
        );
    }
    @ParameterizedTest
    @MethodSource
    void filterComplexWordsShouldNotFilter(String companyToFilter) {
        String filtered = wordFilter.filterComplexWords(companyToFilter);

        assertEquals(companyToFilter, filtered);
    }
}