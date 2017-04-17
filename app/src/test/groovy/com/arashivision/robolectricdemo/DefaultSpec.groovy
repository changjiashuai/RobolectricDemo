package com.arashivision.robolectricdemo

import com.arashivision.robolectricdemo.ui.MainActivity
import org.robolectric.Robolectric
import spock.lang.Specification

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/4/7 14:53.
 */
public class DefaultSpec extends Specification {

    def "should find button text and compare text value"() {
        given:
        def mainActivity = Robolectric.setupActivity(MainActivity.class);

        when:
        def text = mainActivity.mBtnLogin.text

        then:
        text == "Login"
    }

    def "length of Spock's and his friends' names"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }
}
