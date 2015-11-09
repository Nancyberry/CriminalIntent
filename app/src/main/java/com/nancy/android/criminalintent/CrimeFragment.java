package com.nancy.android.criminalintent;

//import android.app.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by nan.zhang on 4/29/15.
 */
public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "com.nancy.android.criminalintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_CONTACT = 2;

    // nancy added date style
    private static final String SIMPLE_STYLE = "simple";

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mAddCrimeButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private Button mSuspectButton;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onCrimeUpdated(Crime crime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCrime = new Crime();

//        UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        UUID crimeId = (UUID) this.getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        // inform fragment manager to response to option menu
        setHasOptionsMenu(true);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstaceState) {
        // produce view
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        // enable ancestral navigation, only for version > 11 and has father activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString());
                mCallbacks.onCrimeUpdated(mCrime);
                getActivity().setTitle(mCrime.getTitle());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
//        Date date = mCrime.getDate();
//        DateFormat df = DateFormat.getDateInstance();
//        String formatedDate = df.format(date);
//        mDateButton.setText(mCrime.getDate().toString());
//        mDateButton.setText(formatedDate);

//        updateDate();
        updateDate(SIMPLE_STYLE);

//        mDateButton.setEnabled(false);

        // add date dialog
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
//                DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                // set target fragment for DatePickerFragment
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
                mCallbacks.onCrimeUpdated(mCrime);
            }
        });

//        mAddCrimeButton = (Button)v.findViewById(R.id.add_crime);
//        mAddCrimeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavUtils.navigateUpFromSameTask(getActivity());
//            }
//        });


        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch the camera activity
                Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        // if camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            mPhotoButton.setEnabled(false);
        }

        mPhotoView = (ImageView) v.findViewById(R.id.crime_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p = mCrime.getPhoto();
                if (p == null) {
                    return;
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.createInstance(path).show(fm, DIALOG_IMAGE);
            }
        });

        mSuspectButton = (Button) v.findViewById(R.id.crime_suspectButton);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });
        if (mCrime.getSuspect() != null) {
            mSuspectButton.setText(mCrime.getSuspect());
        }

        Button reportButton = (Button) v.findViewById(R.id.crime_reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));   // force showing chooser, not use default option
                startActivity(i);
            }
        });

        return v;
    }

    private void showPhoto() {
        // (re)set the image button's image based on our photo
        Photo p = mCrime.getPhoto();
        BitmapDrawable b = null;
        if (p != null) {
            String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
            b = PictureUtils.getScaledDrawable(getActivity(), path);
            mPhotoView.setImageDrawable(b);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();    // show photo as soon as the view appears
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);    // destroy photo as soon as the view disappears
    }

    // response to date change in DatePickerFragment
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            // fresh model and view
            mCrime.setDate(date);
//            updateDate();
            mCallbacks.onCrimeUpdated(mCrime);
            updateDate(SIMPLE_STYLE);
        } else if (requestCode == REQUEST_PHOTO) {
            String filename = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Photo p = new Photo(filename);
                mCrime.setPhoto(p);
                mCallbacks.onCrimeUpdated(mCrime);
                showPhoto();
            }
        } else if (requestCode == REQUEST_CONTACT) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY };
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            if (c.getCount() == 0) {
                c.close();
                return;
            }

            c.moveToFirst();
            String suspect = c.getString(0);
            mCrime.setSuspect(suspect);
            mCallbacks.onCrimeUpdated(mCrime);
            mSuspectButton.setText(suspect);
            c.close();
        }
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);     // get string from xml config
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = android.text.format.DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);

        return report;
    }

    // response to option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    getActivity().getActionBar().setSubtitle(null);
                    item.setTitle(R.string.subtitle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // store data
        CrimeLab.get(getActivity()).saveCrimes();
    }

    public void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    public void updateDate(String style) {
        Date date = mCrime.getDate();
        String formatedDate = date.toString();

        if (style.equalsIgnoreCase(SIMPLE_STYLE)) {
//            DateFormat df = DateFormat.getDateInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatedDate = df.format(date);
        }

        mDateButton.setText(formatedDate);
    }
}
