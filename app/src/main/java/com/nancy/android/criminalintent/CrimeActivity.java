//package com.nancy.android.criminalintent;
//
//import android.support.v4.app.Fragment;
//import android.view.MenuItem;
//
//import java.util.UUID;
//
//
//public class CrimeActivity extends SingleFragmentActivity  {
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_fragment);
////
////        FragmentManager fm = getSupportFragmentManager();
////        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
////
////        if (null == fragment) {
////            fragment = new CrimeFragment();
////            fm.beginTransaction()
////                    .add(R.id.fragmentContainer, fragment)
////                    .commit();
////        }
////    }
//
//    @Override
//    protected Fragment createFragment() {
////        return new CrimeFragment();
//        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
//        return CrimeFragment.newInstance(crimeId);
//    }
//
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_crime, menu);
////        return true;
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
