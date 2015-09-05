//package com.nancy.android.criminalintent;
//
//import android.test.ActivityInstrumentationTestCase2;
//
//import com.robotium.solo.Solo;
//
///**
// * Created by nan.zhang on 6/11/15.
// */
//public class robotiumTest extends ActivityInstrumentationTestCase2<CrimeListActivity> {
//
//    private Solo solo;
//
//    public robotiumTest() {
//        super(CrimeListActivity.class);
//    }
//
//    @Override
//    public void setUp() throws Exception {
//        solo = new Solo(getInstrumentation(), getActivity());
//    }
//
//    @Override
//    public void tearDown() throws Exception {
//        solo.finishOpenedActivities();
//    }
//
//    public void testEditCriminal() throws Exception {
//        solo.unlockScreen();
////        solo.clickInList(7);
////        solo.assertCurrentActivity("Expected CrimePager Activity", "CrimePagerActivity");
////        solo.clearEditText(0);
////        solo.typeText(0, "Robotium is typing!");
////        solo.goBack();
////        solo.sleep(1000);
////
////        solo.clickOnCheckBox(2);
////        solo.sleep(1000);
////
//////        solo.goBack();
////        solo.sleep(1000);
//
//        boolean criminalFound = solo.searchText("(?i).*?Crime.*", 22, true);
//        assertTrue("found 100 crime", criminalFound);
//
////        solo.scrollDownList(19);
////        solo.scrollUpList(10);
////        solo.scrollUpList(0);
//
////        solo.scrollDownList()
//    }
//}
