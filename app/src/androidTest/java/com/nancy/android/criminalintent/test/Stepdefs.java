package com.nancy.android.criminalintent.test;

import android.test.ActivityInstrumentationTestCase2;

import com.nancy.android.criminalintent.Belly;
import com.nancy.android.criminalintent.CrimeListActivity;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;

@CucumberOptions(features = "features")
public class Stepdefs extends ActivityInstrumentationTestCase2<CrimeListActivity> {

    public Stepdefs(SomeDependency someDependency) {
        super(CrimeListActivity.class);
    }

    @Given("^I have (\\d+) cukes in my belly$")
    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
        Belly belly = new Belly();
        belly.eat(cukes);
    }
}
