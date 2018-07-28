package com.example.shouhei.runscanner.runresult;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shouhei.runscanner.R;
import com.example.shouhei.runscanner.data.Run;
import com.example.shouhei.runscanner.runs.Runs;
import com.example.shouhei.runscanner.util.ElementWrapper;
import com.example.shouhei.runscanner.util.PictureUtils;
import com.example.shouhei.runscanner.util.RightSideElementFilter;
import com.example.shouhei.runscanner.util.RightSideElementsCalculator;
import com.example.shouhei.runscanner.util.RightSideElementsList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

public class RunsResultFragment extends Fragment {

    private static final String TAG = "RunsResultFragment";

    private Button mGalleryButton;
    private Button mPhotoButton;
    private ImageView mTargetImageView;
    private Button mDummyButton;
    private EditText mDistanceField;
    private EditText mCaloriesField;
    private EditText mDurationField;
    private EditText mAvgPaceField;
    private EditText mAvgHeartRateField;
    private FloatingActionButton mScanFab;
    private FloatingActionButton mDoneFab;

    private File mPhotoFile;
    private Uri mTargetUri;
    private Run mResultRun;
    private int mTargetImageWidth;
    private int mTargetImageHeight;
    private boolean mIsScanned;

    private static final int REQUEST_PHOTO = 0;
    private static final int REQUEST_GALLERY = 1;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView is called.");

        View root = inflater.inflate(R.layout.runresult_frag, container, false);

        mResultRun = new Run();

        // set up the Gallery button
        mGalleryButton = root.findViewById(R.id.gallery_button);
        mGalleryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "GalleryButton clicked");

                        Intent pickupImage = new Intent(Intent.ACTION_GET_CONTENT);
                        pickupImage.setType("image/*");
                        startActivityForResult(pickupImage, REQUEST_GALLERY);
                    }
                });

        // set up the Photo button
        mPhotoButton = root.findViewById(R.id.photo_button);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto =
                captureImage.resolveActivity(getActivity().getPackageManager()) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "PhotoButton clicked");

                        Run run = new Run();

                        // TODO change where run instance is added into runs. add it after scan.
                        //            Runs.get(RunResultActivity.this).addRun(run);
                        mPhotoFile = Runs.get(getActivity()).getPhotoFile(run);
                        Uri uri =
                                FileProvider.getUriForFile(
                                        getActivity(),
                                        "com.example.shouhei.runscanner.fileprovider",
                                        mPhotoFile);

                        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        // TODO change to way to grant permission that is compatible to Android5.0-?
                        captureImage.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        startActivityForResult(captureImage, REQUEST_PHOTO);
                    }
                });

        mTargetImageView = root.findViewById(R.id.run_photo);

        mDistanceField = root.findViewById(R.id.distance_value);
        mDistanceField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mResultRun.setDistance(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

        mDurationField = root.findViewById(R.id.duration_value);
        mDurationField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mResultRun.setDuration(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

        mCaloriesField = root.findViewById(R.id.calories_value);
        mCaloriesField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mResultRun.setCalorie(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

        mAvgPaceField = root.findViewById(R.id.avg_pace_value);
        mAvgPaceField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mResultRun.setAvePace(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

        mAvgHeartRateField = root.findViewById(R.id.avg_heart_rate_value);
        mAvgHeartRateField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mResultRun.setAveHeartRate(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

        mScanFab = root.findViewById(R.id.scan_fab);
        mScanFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "DummyButton clicked");
                        InputStream stream = null;
                        try {
                            stream = getActivity().getContentResolver().openInputStream(mTargetUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(stream));

                        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                        FirebaseVisionTextDetector detector =
                                FirebaseVision.getInstance().getVisionTextDetector();

                        detector.detectInImage(image)
                                .addOnSuccessListener(
                                        new OnSuccessListener<FirebaseVisionText>() {
                                            @Override
                                            public void onSuccess(
                                                    FirebaseVisionText firebaseVisionText) {
                                                // Task completed successfully
                                                Log.d(TAG, "Task completed successfully");

                                                RightSideElementsList elementsList =
                                                        new RightSideElementsList(
                                                                mTargetImageWidth,
                                                                mTargetImageHeight);
                                                debugFirebaseVisionText(firebaseVisionText);
                                                for (FirebaseVisionText.Block block :
                                                        firebaseVisionText.getBlocks()) {
                                                    for (FirebaseVisionText.Line line :
                                                            block.getLines()) {
                                                        for (FirebaseVisionText.Element element :
                                                                line.getElements()) {
                                                            elementsList.add(element);
                                                        }
                                                    }
                                                }
                                                RightSideElementsCalculator calculator =
                                                        new RightSideElementsCalculator(
                                                                elementsList);
                                                Log.d(
                                                        TAG,
                                                        "===================[calculation result]===================");
                                                Log.d(
                                                        TAG,
                                                        "mean x = "
                                                                + String.valueOf(
                                                                        calculator.getMeanX()));
                                                Log.d(
                                                        TAG,
                                                        "order x = "
                                                                + String.valueOf(
                                                                        calculator.getOrderX()));
                                                Log.d(
                                                        TAG,
                                                        "variance x = "
                                                                + String.valueOf(
                                                                        calculator.getVarianceX()));
                                                BigDecimal coefficientX =
                                                        new BigDecimal(
                                                                calculator.getCoefficientX() * 100);
                                                coefficientX =
                                                        coefficientX.setScale(
                                                                4, BigDecimal.ROUND_HALF_UP);
                                                Log.d(
                                                        TAG,
                                                        "coefficient x = "
                                                                + coefficientX.toString()
                                                                + "%");
                                                Log.d(
                                                        TAG,
                                                        "mean y = "
                                                                + String.valueOf(
                                                                        calculator.getMeanY()));
                                                Log.d(
                                                        TAG,
                                                        "order y = "
                                                                + String.valueOf(
                                                                        calculator.getOrderY()));
                                                Log.d(
                                                        TAG,
                                                        "variance y = "
                                                                + String.valueOf(
                                                                        calculator.getVarianceY()));
                                                Log.d(
                                                        TAG,
                                                        "coefficient y = "
                                                                + String.valueOf(
                                                                        Math.round(
                                                                                calculator
                                                                                                .getCoefficientY()
                                                                                        * 100))
                                                                + "%");

                                                elementsList.sortByY();
                                                new RightSideElementFilter(calculator).filter();

                                                Log.d(
                                                        TAG,
                                                        "===================[after sort by Y]===================");
                                                int i = 0;
                                                for (ElementWrapper e :
                                                        elementsList.getElementList()) {
                                                    Log.d(
                                                            TAG,
                                                            "Result#"
                                                                    + i
                                                                    + " : "
                                                                    + e.getValue()
                                                                    + " | (x,y) = ("
                                                                    + e.getX()
                                                                    + ","
                                                                    + e.getY()
                                                                    + ")");
                                                    i++;
                                                }
                                                Log.d(
                                                        TAG,
                                                        "===================[get values]===================");
                                                Log.d(
                                                        TAG,
                                                        "miles : "
                                                                + elementsList.getDistanceValue());
                                                Log.d(
                                                        TAG,
                                                        "calories : "
                                                                + elementsList.getCaloriesValue());
                                                Log.d(
                                                        TAG,
                                                        "duration : "
                                                                + elementsList.getDurationValue());
                                                Log.d(
                                                        TAG,
                                                        "avg pace : "
                                                                + elementsList.getAvgPaceValue());
                                                Log.d(
                                                        TAG,
                                                        "avg heart rate : "
                                                                + elementsList.getAvgHeartRate());

                                                mDistanceField.setText(
                                                        elementsList.getDistanceValue());
                                                mCaloriesField.setText(
                                                        elementsList.getCaloriesValue());
                                                mDurationField.setText(
                                                        elementsList.getDurationValue());
                                                mAvgPaceField.setText(
                                                        elementsList.getAvgPaceValue());
                                                mAvgHeartRateField.setText(
                                                        elementsList.getAvgHeartRate());

                                                mIsScanned = true;
                                                setFab();
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                Log.d(TAG, "Task failed with an exception");
                                            }
                                        });
                    }
                });

        mDoneFab = root.findViewById(R.id.done_fab);
        mDoneFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar =
                                Snackbar.make(v, "Your run is added", Snackbar.LENGTH_LONG);
                        // TODO Implement 'UNDO' function.
                        snackbar.setAction(
                                "UNDO",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {}
                                });
                        snackbar.show();
                        snackbar.addCallback(
                                new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(
                                            Snackbar transientBottomBar, int event) {
                                        Runs.get(getActivity()).addRun(mResultRun);
                                        getActivity().finish();
                                    }
                                });
                    }
                });

        mIsScanned = false;
        setFab();
        return root;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart() is called.");
        super.onStart();

        // reset state
        mIsScanned = false;
        setFab();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume() is called.");
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO) {

            Uri uri =
                    FileProvider.getUriForFile(
                            getActivity(),
                            "com.example.shouhei.runscanner.fileprovider",
                            mPhotoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        } else if (requestCode == REQUEST_GALLERY) {
            mTargetUri = data.getData();
            Log.d(TAG, "Schema : " + data.getScheme()); // content
            Log.d(TAG, "Type : " + data.getType()); // null
            Log.d(TAG, "Flag : " + data.getFlags()); // 1
            Log.d(
                    TAG,
                    "URI : "
                            + mTargetUri
                                    .toString()); // content://com.google.android.apps.photos.contentprovider/0/1/mediakey%3A%2Flocal%253A9ff410cd-a6d7-4f90-8aaf-285b8ce54161/ORIGINAL/NONE/151343972
            Log.d(
                    TAG,
                    "Authority : "
                            + mTargetUri
                                    .getAuthority()); // com.google.android.apps.photos.contentprovider

            try {
                InputStream stream = getActivity().getContentResolver().openInputStream(mTargetUri);
                Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(stream));
                mTargetImageWidth = bitmap.getWidth();
                mTargetImageHeight = bitmap.getHeight();
                Log.d(
                        TAG,
                        "bitmap image size width : "
                                + bitmap.getWidth()
                                + " height "
                                + bitmap.getHeight());
                mTargetImageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // TODO implement copy the file into shared local storage same as photo.
        }
    }

    private void updatePhotoView() {

        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mTargetImageView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mTargetImageView.setImageBitmap(bitmap);
        }
    }

    private void setFab() {
        if (!mIsScanned) {
            // before scan
            mScanFab.setVisibility(View.VISIBLE);
            mDoneFab.setVisibility(View.INVISIBLE);
        } else {
            // after scan
            mScanFab.setVisibility(View.INVISIBLE);
            mDoneFab.setVisibility(View.VISIBLE);
        }
    }

    private void debugFirebaseVisionText(FirebaseVisionText firebaseVisionText) {
        int i = 1;
        int j = 1;
        int k = 1;
        for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
            Point[] blockCornerPoints = block.getCornerPoints();

            Log.d(TAG, "Block#" + i + " [" + block.getText() + "]");
            for (int l = 0; l < blockCornerPoints.length; l++) {
                Log.d(
                        TAG,
                        "Block#"
                                + i
                                + " Point "
                                + l
                                + " (x,y) = ("
                                + blockCornerPoints[l].x
                                + ","
                                + blockCornerPoints[l].y
                                + ")");
            }
            i++;
            for (FirebaseVisionText.Line line : block.getLines()) {
                Log.d(TAG, "    Line#" + j + " [" + line.getText() + "]");
                Point[] lienCornerPoints = line.getCornerPoints();
                for (int l = 0; l < lienCornerPoints.length; l++) {
                    Log.d(
                            TAG,
                            "    Line#"
                                    + j
                                    + " Point "
                                    + l
                                    + " (x,y) = ("
                                    + lienCornerPoints[l].x
                                    + ","
                                    + lienCornerPoints[l].y
                                    + ")");
                }
                j++;
                for (FirebaseVisionText.Element element : line.getElements()) {
                    Log.d(TAG, "        Element#" + k + " [" + element.getText() + "]");
                    Point[] elementCornerPoints = element.getCornerPoints();
                    for (int l = 0; l < elementCornerPoints.length; l++) {
                        Log.d(
                                TAG,
                                "        Element#"
                                        + k
                                        + " Point "
                                        + l
                                        + " (x,y) = ("
                                        + elementCornerPoints[l].x
                                        + ","
                                        + elementCornerPoints[l].y
                                        + ")");
                    }
                    k++;
                }
            }
        }
    }
}
