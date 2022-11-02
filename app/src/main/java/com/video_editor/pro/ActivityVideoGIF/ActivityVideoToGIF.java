package com.video_editor.pro.ActivityVideoGIF;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.video_editor.pro.ActivityAudioCompress.ActivityAudioCompressor;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.AppodealConstant;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
public class ActivityVideoToGIF extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    public static String outputPath;
    public static Bitmap thumb;
    File file;
    String string;


    String string1 = null;
    Boolean aBoolean = Boolean.valueOf(false);
    String string2 = "00";
    ImageView imageView;
    public FFmpeg ffmpeg;
    EditorVideoSliceSeekBar editorVideoSliceSeekBar;
    OnClickListener h = new OnClickListener() {
        @Override public void onClick(View view) {
            if (ActivityVideoToGIF.this.aBoolean.booleanValue()) {
                ActivityVideoToGIF.this.imageView.setBackgroundResource(R.drawable.play2);
                ActivityVideoToGIF.this.aBoolean = Boolean.valueOf(false);
            } else {
                ActivityVideoToGIF.this.imageView.setBackgroundResource(R.drawable.pause2);
                ActivityVideoToGIF.this.aBoolean = Boolean.valueOf(ActivityVideoToGIF.BOOLEAN);
            }
            ActivityVideoToGIF.this.e();
        }
    };
    private PowerManager powerManager;
    public TextView textView;
    public TextView textView1;
    private TextView textView2;
    public TextView textView3;
    public EditorVideoPlayerState editorVideoPlayerState = new EditorVideoPlayerState();
    private a p = new a();
    public VideoView videoView;
    private WakeLock wakeLock;


    private class a extends Handler {
        private boolean aBoolean1;
        private Runnable runnable;

        private a() {
            this.aBoolean1 = false;
            this.runnable = new Runnable() {
                public void run() {
                    a.this.a();
                }
            };
        }


        public void a() {
            if (!this.aBoolean1) {
                this.aBoolean1 = ActivityVideoToGIF.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean1 = false;
            ActivityVideoToGIF.this.editorVideoSliceSeekBar.videoPlayingProgress(ActivityVideoToGIF.this.videoView.getCurrentPosition());
            if (!ActivityVideoToGIF.this.videoView.isPlaying() || ActivityVideoToGIF.this.videoView.getCurrentPosition() >= ActivityVideoToGIF.this.editorVideoSliceSeekBar.getRightProgress()) {
                if (ActivityVideoToGIF.this.videoView.isPlaying()) {
                    ActivityVideoToGIF.this.videoView.pause();
                    ActivityVideoToGIF.this.aBoolean = Boolean.valueOf(false);
                    ActivityVideoToGIF.this.videoView.seekTo(100);
                    ActivityVideoToGIF.this.imageView.setBackgroundResource(R.drawable.play2);
                }
                ActivityVideoToGIF.this.editorVideoSliceSeekBar.setSliceBlocked(false);
                ActivityVideoToGIF.this.editorVideoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.runnable, 50);
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_to_gif);



        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video To GIF");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        Appodeal.initialize(this, getString(R.string.appodeal_appkey), Appodeal.BANNER|Appodeal.INTERSTITIAL|Appodeal.MREC, false);
        Appodeal.setMrecViewId(R.id.appodealMrecView);
        Appodeal.show(this, Appodeal.MREC);
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            f();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VID_GIF-");
            stringBuilder.append(System.currentTimeMillis() / 1000);
            String sb2 = stringBuilder.toString();
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            stringBuilder1.append("/");
            stringBuilder1.append(getResources().getString(R.string.MainFolderName));
            stringBuilder1.append("/");
            stringBuilder1.append(getResources().getString(R.string.VideoToGIF));
            stringBuilder1.append("/");
            this.file = new File(stringBuilder1.toString());
            if (!this.file.exists()) {
                this.file.mkdirs();
            }
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(this.file.getAbsolutePath()));
            stringBuilder2.append("/");
            stringBuilder2.append(sb2);
            outputPath = stringBuilder2.toString();
            this.textView = findViewById(R.id.left_pointer);
            this.textView1 = findViewById(R.id.right_pointer);
            this.imageView = findViewById(R.id.buttonply);
            this.editorVideoSliceSeekBar = findViewById(R.id.seek_bar);
            this.videoView = findViewById(R.id.videoView1);
            this.textView2 = findViewById(R.id.Filename);
            this.textView3 = findViewById(R.id.dur);
            this.powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.wakeLock = this.powerManager.newWakeLock(6, "My Tag");
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                this.editorVideoPlayerState = (EditorVideoPlayerState) lastNonConfigurationInstance;
            } else {
                Bundle extras = getIntent().getExtras();
                this.editorVideoPlayerState.setFilename(extras.getString("videoPath"));
                this.string1 = extras.getString("videoPath");
                thumb = ThumbnailUtils.createVideoThumbnail(this.editorVideoPlayerState.getFilename(), 1);
            }
            this.textView2.setText(new File(this.string1).getName());
            d();
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ActivityVideoToGIF.this.aBoolean = Boolean.valueOf(false);
                    ActivityVideoToGIF.this.imageView.setBackgroundResource(R.drawable.play2);
                }
            });
            this.imageView.setOnClickListener(this.h);

            Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                @Override
                public void onInterstitialLoaded(boolean isPrecache) {
                    // Called when interstitial is loaded
                }
                @Override
                public void onInterstitialFailedToLoad() {
                    // Called when interstitial failed to load
                }
                @Override
                public void onInterstitialShown() {
                    // Called when interstitial is shown
                }
                @Override
                public void onInterstitialShowFailed() {
                    // Called when interstitial show failed
                }
                @Override
                public void onInterstitialClicked() {
                    // Called when interstitial is clicked
                }
                @Override
                public void onInterstitialClosed() {
                    // Called when interstitial is closed
                    ActivityVideoToGIF.this.c();
                }
                @Override
                public void onInterstitialExpired()  {
                    // Called when interstitial is expired
                }
            });

            return;
        }
        throw new AssertionError();
    }

    public void b() {
        if(AppodealConstant.ads==2){
            AppodealConstant.ads = 0;
            if(Appodeal.isLoaded(Appodeal.INTERSTITIAL)){
                Appodeal.show(this, Appodeal.INTERSTITIAL);
            }else {
                c();
            }
        }else {
            AppodealConstant.ads=AppodealConstant.ads+1;
        }

    }



    public void c() {
        Intent intent = new Intent(this, ActivityGIFPreview.class);
        intent.putExtra("videourl", outputPath);
        intent.putExtra("isfrommain", BOOLEAN);
        startActivity(intent);
    }















    public void gifcommand() {
        String valueOf = String.valueOf(this.editorVideoPlayerState.getStart() / 1000);
        String valueOf2 = String.valueOf(this.editorVideoPlayerState.getDuration() / 1000);
        new MediaMetadataRetriever().setDataSource(this.editorVideoPlayerState.getFilename());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.MainFolderName));
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.VideoToGIF));
        stringBuilder.append("/");
        stringBuilder.append(outputPath.substring(outputPath.lastIndexOf("/") + 1));
        stringBuilder.append(".gif");
        outputPath = stringBuilder.toString();
        a(new String[]{"-y", "-ss", valueOf, "-t", valueOf2, "-i", this.string1, "-f", "gif", "-b", "2000k", "-r", "10", "-s", "320x240", outputPath}, outputPath);
    }

    private void a(String[] strArr, final String str) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            this.ffmpeg.execute(strArr, new ExecuteBinaryResponseHandler() {
                @Override public void onFailure(String str) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        ActivityVideoToGIF.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoToGIF.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    ActivityVideoToGIF.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(ActivityVideoToGIF.outputPath))));
                    ActivityVideoToGIF.this.b();
                }

                @Override public void onProgress(String str) {
                    Log.d("ffmpegResponse", str);
                    StringBuilder sb = new StringBuilder();
                    sb.append("progress : ");
                    sb.append(str);
                    progressDialog.setMessage(sb.toString());
                }

                @Override public void onStart() {
                    progressDialog.setMessage("Processing...");
                }

                @Override public void onFinish() {
                    progressDialog.dismiss();
                    ActivityVideoToGIF.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    private void d() {
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoToGIF.this.editorVideoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoToGIF.this.editorVideoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityVideoToGIF.this.videoView.seekTo(ActivityVideoToGIF.this.editorVideoSliceSeekBar.getLeftProgress());
                        }
                        ActivityVideoToGIF.this.textView.setText(ActivityVideoToGIF.getTimeForTrackFormat(i, ActivityVideoToGIF.BOOLEAN));
                        ActivityVideoToGIF.this.textView1.setText(ActivityVideoToGIF.getTimeForTrackFormat(i2, ActivityVideoToGIF.BOOLEAN));
                        ActivityVideoToGIF.this.string2 = ActivityVideoToGIF.getTimeForTrackFormat(i, ActivityVideoToGIF.BOOLEAN);
                        ActivityVideoToGIF.this.editorVideoPlayerState.setStart(i);
                        ActivityVideoToGIF.this.string = ActivityVideoToGIF.getTimeForTrackFormat(i2, ActivityVideoToGIF.BOOLEAN);
                        ActivityVideoToGIF.this.editorVideoPlayerState.setStop(i2);
                        TextView g = ActivityVideoToGIF.this.textView3;
                        StringBuilder sb = new StringBuilder();
                        sb.append("duration : ");
                        int i3 = (i2 / 1000) - (i / 1000);
                        sb.append(String.format("%02d:%02d:%02d", Integer.valueOf(i3 / 3600), Integer.valueOf((i3 % 3600) / 60), Integer.valueOf(i3 % 60)));
                        g.setText(sb.toString());
                    }
                });
                ActivityVideoToGIF.this.string = ActivityVideoToGIF.getTimeForTrackFormat(mediaPlayer.getDuration(), ActivityVideoToGIF.BOOLEAN);
                ActivityVideoToGIF.this.editorVideoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoToGIF.this.editorVideoSliceSeekBar.setLeftProgress(0);
                ActivityVideoToGIF.this.editorVideoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoToGIF.this.editorVideoSliceSeekBar.setProgressMinDiff(0);
                ActivityVideoToGIF.this.videoView.seekTo(100);
            }
        });
        this.videoView.setVideoPath(this.editorVideoPlayerState.getFilename());
        this.videoView.seekTo(0);
        this.string = getTimeForTrackFormat(this.videoView.getDuration(), BOOLEAN);
    }


    public void e() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.editorVideoSliceSeekBar.setSliceBlocked(false);
            this.editorVideoSliceSeekBar.removeVideoStatusThumb();
            return;
        }
        this.videoView.seekTo(this.editorVideoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.editorVideoSliceSeekBar.videoPlayingProgress(this.editorVideoSliceSeekBar.getLeftProgress());
        this.p.a();
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public static String getTimeForTrackFormat(int i2, boolean z) {
        long j2 = i2;
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }


    @SuppressLint({"WakelockTimeout"})
    @Override public void onResume() {
        super.onResume();
        this.wakeLock.acquire();
        this.videoView.seekTo(this.editorVideoPlayerState.getCurrentTime());
    }


    public void onPause() {
        this.wakeLock.release();
        super.onPause();
        this.editorVideoPlayerState.setCurrentTime(this.videoView.getCurrentPosition());
    }

    private void f() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoToGIF.this.g();
                    Log.d("ffmpeg loading failed! ", "");
                }

                @Override public void onFinish() {
                    Log.d("ffmpeg loading finish! ", "");
                }

                @Override public void onStart() {
                    Log.d("ffmpeg loading started!", "");
                }

                @Override public void onSuccess() {
                    Log.d("ffmpeg loading success!", "");
                }
            });
        } catch (FFmpegNotSupportedException unused) {
            g();
        }
    }


    public void g() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityVideoToGIF.this.finish();
            }
        }).create().show();
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(uri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                new File(str).delete();
                refreshGallery(str);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        query.close();
    }

    public void refreshGallery(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }

    @Override public void onBackPressed() {
        Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
                this.imageView.setBackgroundResource(R.drawable.play2);
            }
            gifcommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
