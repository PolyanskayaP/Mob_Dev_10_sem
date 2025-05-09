package ru.mirea.polyanskayapa.audiorecord;

import	androidx.annotation.NonNull;
import	androidx.appcompat.app.AppCompatActivity;
import	androidx.core.app.ActivityCompat;
import	androidx.core.content.ContextCompat;
import	android.Manifest;
import	android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import	android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import ru.mirea.polyanskayapa.audiorecord.databinding.ActivityMainBinding;

public	class	MainActivity	extends	AppCompatActivity	{
    private	static	final	int	REQUEST_CODE_PERMISSION	=	200;
    private	boolean	isWork;
    private		ActivityMainBinding	binding;
    private	final	String	TAG	=	MainActivity.class.getSimpleName();
    private	String	fileName	=	null;
    private	Button	recordButton	=	null;
    private Button playButton	=	null;
    private	MediaRecorder	recorder	=	null;
    private MediaPlayer player	=	null;
    boolean	isStartRecording	=	true;
    boolean	isStartPlaying	=	true;
    @Override
    protected	void	onCreate(Bundle	savedInstanceState)	{
        super.onCreate(savedInstanceState);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int	audioRecordPermissionStatus	=	ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        int	storagePermissionStatus	=	ContextCompat.checkSelfPermission(this,	android.Manifest.permission.
                WRITE_EXTERNAL_STORAGE);
        if	(audioRecordPermissionStatus	==	PackageManager.PERMISSION_GRANTED	&&	storagePermissionStatus
                ==	PackageManager.PERMISSION_GRANTED)	{
            isWork	=	true;
        }	else	{
            //	Выполняется	запрос	к	пользователь	на	получение	необходимых	разрешений
            ActivityCompat.requestPermissions(this,	new	String[]	{Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE},	REQUEST_CODE_PERMISSION);
        }
        recordButton	=	binding.recordButton;
        playButton	=	binding.playButton;
        playButton.setEnabled(false);
        fileName	=	(new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp")).getAbsolutePath();
        recordButton.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View	v)	{
                if	(isStartRecording)	{
                    recordButton.setText("Stop	recording");
                    startRecording();
                    playButton.setEnabled(false);
                }	else	{
                    recordButton.setText("Start	recording");
                    playButton.setEnabled(true);
                    stopRecording();
                }
                isStartRecording	=	!isStartRecording;
            }
        });
        playButton.setOnClickListener(new	View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartPlaying) {
                    playButton.setText("Stop playing");
                    recordButton.setEnabled(false);
                    startPlaying();
                } else {
                    playButton.setText("Start playing");
                    recordButton.setEnabled(false);
                    stopPlaying();
                }
                isStartPlaying = !isStartPlaying;
            }
        });
    }
    @Override
    public	void	onRequestPermissionsResult(int	requestCode,	@NonNull	String[]	permissions,	@NonNull	int[]
            grantResults)	{
        //	производится	проверка	полученного	результата	от	пользователя	на	запрос	разрешения	Camera
        super.onRequestPermissionsResult(requestCode,	permissions,	grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if	(!isWork	)	finish();
    }
    private	void	startRecording()	{
        recorder	=	new	MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try	{
            recorder.prepare();
        }	catch	(IOException e)	{
            Log.e(TAG,	"prepare()	failed");
        }
        recorder.start();
    }
    private	void	stopRecording()	{
        recorder.stop();
        recorder.release();
        recorder	=	null;
    }
    private	void	startPlaying()	{
        player	=	new	MediaPlayer();
        try	{
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        }	catch	(IOException	    e)	{
            Log.e(TAG,	"prepare()	failed");
        }
    }
    private	void	stopPlaying()	{
        player.release();
        player	=	null;
    }
}