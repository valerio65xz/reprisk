package com.reprisk.companiesnews.parser;

import com.reprisk.companiesnews.BaseUnitTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArticleParserTest extends BaseUnitTest {

    private final ArticleParser articleParser = new ArticleParser();

    private final String article1 = """
            <?xml version="1.0" ?><news-item id="000D-2695-EAE6-7998"><date>2014-03-15</date><title>Arctic trek is snow joke for wounded Army heroes; PICK OF THE WEEK</title><source>The Sentinel (Stoke)</source><author>JOHN WOODHOUSE</author><text><![CDATA["I 'M terrified for myself to be honest, hugely daunted, so I can't even begin to imagine what it's going to be like for them." So says Prince Harry ahead of his epic 200km trek to the South Pole with wounded servicemen and women, as seen in Harry's South Pole Heroes (Sun, ITV, 8pm). Led by the prince, the team taking part in the fundraising expedition include a double leg amputee and the first British woman to lose a limb on the frontline.
            In the first episode, the expedition members undergo an intensive training regime, including a night in a specialised cold chamber normally used for testing vehicles, before Harry introduces them to grandma at Buckingham Palace.
            On a training exercise in Iceland, meanwhile, they learn the realities of skiing for up to ten hours a day, hauling 80kgs of kit in temperatures as low as -40C. "To take a double amputee to the South Pole," ponders Harry, "it's really going to be quite a moving moment when we get there. For me, it's bigger than just these guys. We are trying to raise money, but also to raise awareness of the fact that the injuries they have sustained, they are going to carry for the rest of their lives."
            More high exertion in Davina - Beyond Breaking Point For Sport Relief (Mon, BBC1, 9pm), charting Davina McCall's battle to run, swim, and cycle 450 miles from Edinburgh to London.
            The programme, in particular, reveals the inside story of the dramatic third day when the challenge became headline news as an exhausted McCall had to be carried ashore from Lake Windermere, explaining how her medical and training team had her back on her feet and cycling 60 miles just one hour later.
            We also witness the presenter's fight against fearsome weather, culminating in an emotional and overwhelming reunion with family and friends as she completes her epic challenge.
            What drove Davina on, we discover, was memories of her recent trip to Kenya where she met Anne, a young girl working in a quarry, breaking rocks with her family. Prostitution: What''s The Harm? (Mon, BBC3, 10pm) reveals how, revolutionised by the internet and serviced by an estimated 100,000 sex workers, prostitution in Britain is thriving.
            Billie JD Porter talks to the young men who pay for sex, and the women selling their bodies, such as 27-year-old Charlotte, who explains how she exploits social media to maximise her business opportunities, and explains why she finds it such a satisfying job.
            But Porter also investigates prostitution's darker side - 140 sex workers have been murdered in Britain since 1990. She visits the site of one such killing and also hears from a former sex worker about the violence and degradation she experienced, and the emotional damage it wreaked on her life.
            Finally, she meets former madam Becky Adams to see what it takes to set up and run a brothel, and quizzes one of the country's leading police officers on legalisation. Those who enjoyed exceptional pre-Olympic sitcom TwentyTwelve will be delighted to see that the team has been reassembled to oversee a transformation of the BBC. In W1A (Wed, BBC2, 9pm), the situation may have changed but, for boss Ian Fletcher, the irritations look horribly familiar. His first challenge on arriving at New Broadcasting House (on his new and much improved folding bike), is to find somewhere to sit in a building aggressively over designed around the principle of not having a desk.
            He soon finds himself holding the hottest of hot potatoes when activists complain the Cornish are shamefully under-represented on the BBC.
            Matters worsen when BBC Spotlight South West presenter Sally Wingate goes public with her feeling that her failure to progress to a national presenting role might be part of the BBC's institutionally anti-Cornish bias.
            Ideas how best to limit the damage lead to PR company Perfect Curve's Siobhan Sharpe suggesting getting Sally "to do something on Springwatch or some kind of Bake Off".
            Meanwhile, producer Lucy Freeman is having meetings with Head of Output Anna Rampton and Entertainment Format Creative David Wilkes about a forthcoming new flagship show - Britain's Tastiest Village - which according to David is 'kind of Britain's Got Talent meets Countryfile with a bit of The One Show thrown in just in case'. Hugh Bonneville and Jessica Hynes lead the worryingly realistic nonsense.
            ]]></text></news-item>""";

    private final String article2 = """
            <?xml version="1.0" ?><news-item id="00A2-21FD-517C-F739"><date>2014-03-15</date><title>Mexico to draw line on vigilantes</title><source>The Associated Press</source><author>MARK STEVENSON, Associated Press</author><text><![CDATA[MEXICO CITY (AP) - Mexican authorities have finally served notice to vigilantes fighting a drug cartel in western Michoacan state that their illegal tactics will no longer be tolerated, starting with a string of arrests this week.
            ]]></text></news-item>""";

    private final List<String> wordList = Arrays.asList(
            "Prince Harry",
            "South Pole",
            "Harry's South Pole Heroes",
            "Sun",
            "ITV",
            "Led",
            "British",
            "Harry",
            "Buckingham Palace",
            "Iceland",
            "For",
            "More",
            "Davina",
            "Beyond Breaking Point For Sport Relief",
            "Mon",
            "BBC1",
            "Davina McCall's",
            "Edinburgh",
            "London",
            "The",
            "McCall",
            "Lake Windermere",
            "What",
            "Kenya",
            "Anne",
            "Prostitution",
            "What''s The Harm",
            "BBC3",
            "Britain",
            "Billie JD Porter",
            "Charlotte",
            "But Porter",
            "She",
            "Finally",
            "Becky Adams",
            "Those",
            "TwentyTwelve",
            "BBC",
            "In W1A",
            "Wed",
            "BBC2",
            "Ian Fletcher",
            "His",
            "New Broadcasting House",
            "Cornish",
            "Matters",
            "BBC Spotlight South West",
            "Sally Wingate",
            "BBC's",
            "Ideas",
            "Perfect Curve's Siobhan Sharpe",
            "Sally",
            "Springwatch",
            "Bake Off",
            "Meanwhile",
            "Lucy Freeman",
            "Head",
            "Output Anna Rampton",
            "Entertainment Format Creative David Wilkes",
            "Britain's Tastiest Village",
            "David",
            "Britain's Got Talent",
            "Countryfile",
            "The One Show",
            "Hugh Bonneville",
            "Jessica Hynes");

    @Test
    void getPotentialCompaniesContainsAll() {
        Set<String> potentialCompanies = articleParser.getPotentialCompanies(article1);

        assertTrue(wordList.containsAll(potentialCompanies));
    }

    @Test
    void getPotentialCompaniesContainsNone() {
        Set<String> potentialCompanies = articleParser.getPotentialCompanies(article2);

        assertFalse(potentialCompanies.stream().anyMatch(wordList::contains));
    }

}