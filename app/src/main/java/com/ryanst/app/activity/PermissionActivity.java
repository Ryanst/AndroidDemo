package com.ryanst.app.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.ryanst.app.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by kevin on 16/4/27.
 */
public class PermissionActivity extends BaseActivity {

    final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_test);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request_contact_permission:
                PackageManager pm = getPackageManager();

                int hasPermW = pm.checkPermission(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        getPackageName());
                int hasPermR = pm.checkPermission(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        getPackageName());

                LinkedHashMap<String, ArrayList<PhotoFileItem>> folderMap;

                try {
                    boolean isPer = ContextCompat.checkSelfPermission(getApplicationContext(), "Manifest.permission.READ_EXTERNAL_STORAGE") ==
                            PackageManager.PERMISSION_GRANTED;

                    folderMap = getImageFolderList(PermissionActivity.this);
                } catch (SecurityException se) {
                    se.printStackTrace();
                } finally {
                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_request_read_file_permission:
                try {
                    readContact();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }

                boolean isPer = ContextCompat.checkSelfPermission(getApplicationContext(), "Manifest.permission.READ_CONTACTS") ==
                        PackageManager.PERMISSION_DENIED;

                Toast.makeText(getApplicationContext(), isPer ? "yes" : "no", Toast.LENGTH_LONG);
                break;

            case R.id.btn_to_permission_setting:
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.btn_sdcard_test:
                TestSdCard();
        }
    }

    private void TestSdCard() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }


    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    public LinkedHashMap<String, ArrayList<PhotoFileItem>> getImageFolderList(Context context) {
        LinkedHashMap<String, ArrayList<PhotoFileItem>> folderList = new LinkedHashMap<>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                (MediaStore.Images.Media.DATE_ADDED + " DESC"));
        folderList.clear();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    // Integer id = Integer.valueOf(_id);
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String tpath = path.substring(0, path.lastIndexOf(File.separatorChar));
                    tpath = tpath.substring(tpath.lastIndexOf(File.separatorChar) + 1);
                    ArrayList<PhotoFileItem> list = folderList.get(tpath);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    PhotoFileItem item = new PhotoFileItem();
                    item.setPath(path);
                    list.add(item);
                    folderList.put(tpath, list);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return folderList;
    }

    public class PhotoFileItem implements Parcelable {
        private String path;
        private boolean checked = false;

        public PhotoFileItem() {
        }

        protected PhotoFileItem(Parcel in) {
            path = in.readString();
            checked = in.readByte() != 0;
        }

        public final Creator<PhotoFileItem> CREATOR = new Creator<PhotoFileItem>() {
            @Override
            public PhotoFileItem createFromParcel(Parcel in) {
                return new PhotoFileItem(in);
            }

            @Override
            public PhotoFileItem[] newArray(int size) {
                return new PhotoFileItem[size];
            }
        };

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(path);
            dest.writeByte((byte) (checked ? 1 : 0));
        }
    }

    private void readContact() {
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
    }

}
