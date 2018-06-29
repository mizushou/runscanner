package com.example.shouhei.runscanner.runresult;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import java.text.DecimalFormat;
import java.util.List;

public class RunResultActivity extends AppCompatActivity {

  private static final String TAG = "RunResultActivity";

  private Button mGalleryButton;
  private Button mPhotoButton;
  private ImageView mTargetImageView;
  private Button mDummyButton;
  private EditText mDistanceField;
  private EditText mCaloriesField;
  private EditText mDurationField;
  private EditText mAvgPaceField;
  private EditText mAvgHeartRateField;
  private FloatingActionButton mDoneFab;
  private File mPhotoFile;
  private Uri mTargetUri;
  //  private Task<FirebaseVisionText> mResult;
  private Run mResultRun;

  private int mTargetImageWidth;
  private int mTargetImageHeight;
  private List<Run> mRunList;

  private static final int REQUEST_PHOTO = 0;
  private static final int REQUEST_GALLERY = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called");
    setContentView(R.layout.runresult_act);

    mResultRun = new Run();
    mRunList = Runs.get(this).getRunList();

    mGalleryButton = findViewById(R.id.gallery_button);
    mGalleryButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Log.d(TAG, "GalleryButton clicked");
            //            Intent pickupImage = new Intent(Intent.ACTION_PICK);
            Intent pickupImage = new Intent(Intent.ACTION_GET_CONTENT);
            pickupImage.setType("image/*");
            startActivityForResult(pickupImage, REQUEST_GALLERY);
          }
        });

    mPhotoButton = findViewById(R.id.photo_button);
    final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    // Verify that the intent will resolve to an activity
    boolean canTakePhoto = captureImage.resolveActivity(getPackageManager()) != null;
    mPhotoButton.setEnabled(canTakePhoto);

    mPhotoButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Log.d(TAG, "PhotoButton clicked");

            Run run = new Run();

            // TODO change where run instance is added into runs. add it after scan.
            //            Runs.get(RunResultActivity.this).addRun(run);
            mPhotoFile = Runs.get(RunResultActivity.this).getPhotoFile(run);
            Uri uri =
                FileProvider.getUriForFile(
                    RunResultActivity.this,
                    "com.example.shouhei.runscanner.fileprovider",
                    mPhotoFile);

            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // TODO change to way to grant permission that is compatible to Android5.0-?
            captureImage.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(captureImage, REQUEST_PHOTO);
          }
        });

    mTargetImageView = findViewById(R.id.run_photo);

    mDistanceField = findViewById(R.id.distance_value);
    mDistanceField.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            mResultRun.setDistance(s.toString());
          }

          @Override
          public void afterTextChanged(Editable s) {}
        });

    mDurationField = findViewById(R.id.duration_value);
    mDurationField.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            mResultRun.setDuration(s.toString());
          }

          @Override
          public void afterTextChanged(Editable s) {}
        });

    mCaloriesField = findViewById(R.id.calories_value);
    mCaloriesField.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            mResultRun.setCalory(s.toString());
          }

          @Override
          public void afterTextChanged(Editable s) {}
        });

    mAvgPaceField = findViewById(R.id.avg_pace_value);
    mAvgPaceField.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            mResultRun.setAvePace(s.toString());
          }

          @Override
          public void afterTextChanged(Editable s) {}
        });

    mAvgHeartRateField = findViewById(R.id.avg_heart_rate_value);
    mAvgHeartRateField.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            mResultRun.setAveHeartRate(s.toString());
          }

          @Override
          public void afterTextChanged(Editable s) {}
        });

    mDummyButton = findViewById(R.id.dummy_button);
    mDummyButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            Log.d(TAG, "DummyButton clicked");
            InputStream stream = null;
            try {
              stream = RunResultActivity.this.getContentResolver().openInputStream(mTargetUri);
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(stream));

            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionTextDetector detector =
                FirebaseVision.getInstance().getVisionTextDetector();

            detector
                .detectInImage(image)
                .addOnSuccessListener(
                    new OnSuccessListener<FirebaseVisionText>() {
                      @Override
                      public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        // Task completed successfully
                        Log.d(TAG, "Task completed successfully");

                        RightSideElementsList elementsList =
                            new RightSideElementsList(mTargetImageWidth, mTargetImageHeight);
                        debugFirebaseVisionText(firebaseVisionText);
                        for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
                          for (FirebaseVisionText.Line line : block.getLines()) {
                            for (FirebaseVisionText.Element element : line.getElements()) {
                              elementsList.add(element);
                            }
                          }
                        }
                        RightSideElementsCalculator calculator =
                            new RightSideElementsCalculator(elementsList);
                        Log.d(TAG, "===================[calculation result]===================");
                        Log.d(TAG, "mean x = " + String.valueOf(calculator.getMeanX()));
                        Log.d(TAG, "order x = " + String.valueOf(calculator.getOrderX()));
                        Log.d(TAG, "variance x = " + String.valueOf(calculator.getVarianceX()));
                        BigDecimal coefficientX =
                            new BigDecimal(calculator.getCoefficientX() * 100);
                        coefficientX = coefficientX.setScale(4, BigDecimal.ROUND_HALF_UP);
                        Log.d(TAG, "coefficient x = " + coefficientX.toString() + "%");
                        Log.d(TAG, "mean y = " + String.valueOf(calculator.getMeanY()));
                        Log.d(TAG, "order y = " + String.valueOf(calculator.getOrderY()));
                        Log.d(TAG, "variance y = " + String.valueOf(calculator.getVarianceY()));
                        Log.d(
                            TAG,
                            "coefficient y = "
                                + String.valueOf(Math.round(calculator.getCoefficientY() * 100))
                                + "%");

                        elementsList.sortByY();
                        new RightSideElementFilter(calculator).filter();

                        Log.d(TAG, "===================[after sort by Y]===================");
                        int i = 0;
                        for (ElementWrapper e : elementsList.getElementList()) {
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
                        Log.d(TAG, "===================[get values]===================");
                        Log.d(TAG, "miles : " + elementsList.getDistanceValue());
                        Log.d(TAG, "calories : " + elementsList.getCaloriesValue());
                        Log.d(TAG, "duration : " + elementsList.getDurationValue());
                        Log.d(TAG, "avg pace : " + elementsList.getAvgPaceValue());
                        Log.d(TAG, "avg heart rate : " + elementsList.getAvgHeartRate());

                        mDistanceField.setText(elementsList.getDistanceValue());
                        mCaloriesField.setText(elementsList.getCaloriesValue());
                        mDurationField.setText(elementsList.getDurationValue());
                        mAvgPaceField.setText(elementsList.getAvgPaceValue());
                        mAvgHeartRateField.setText(elementsList.getAvgHeartRate());
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

    mDoneFab = findViewById(R.id.done_fab);
    mDoneFab.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Log.d(TAG, "Done FAB is clicked");
            mRunList.add(mResultRun);
            finish();
          }
        });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_PHOTO) {

      Uri uri =
          FileProvider.getUriForFile(
              RunResultActivity.this, "com.example.shouhei.runscanner.fileprovider", mPhotoFile);
      RunResultActivity.this.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
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
              + mTargetUri.getAuthority()); // com.google.android.apps.photos.contentprovider
      //      Bitmap bitmap = null;
      //      try {
      //        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
      //      } catch (IOException e) {
      //        e.printStackTrace();
      //      }

      try {
        InputStream stream = this.getContentResolver().openInputStream(mTargetUri);
        Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(stream));
        mTargetImageWidth = bitmap.getWidth();
        mTargetImageHeight = bitmap.getHeight();
        Log.d(
            TAG,
            "bitmap image size width : " + bitmap.getWidth() + " height " + bitmap.getHeight());
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
      Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), RunResultActivity.this);
      mTargetImageView.setImageBitmap(bitmap);
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

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }
}
