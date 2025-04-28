package ru.mirea.polyanskayapa.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

import ru.mirea.polyanskayapa.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 01, НОМЕР ПО СПИСКУ: 23, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Ruyanda gorursun");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack:	" + Arrays.toString(mainThread.getStackTrace()));

        binding.button3.setOnClickListener(v -> new  Thread(() -> {
            if (binding.days.getText().toString().length() < 1 || binding.lessons.getText().toString().length() < 1)
                return;
            int days = Integer.parseInt(binding.days.getText().toString());
            int lessons = Integer.parseInt(binding.lessons.getText().toString());
            runOnUiThread(() -> binding.textView.setText(String.valueOf(((float)lessons / (float)days))));
        }).start());
    }
}