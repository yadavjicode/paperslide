package com.example.paper_slide.ui.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.paper_slide.R;

public class DoashBoard extends AppCompatActivity {

    private Button sportsButton, footballButton, cricketButton, hockeyButton,
            f1Button, f2Button, f3Button, c1Button, c2Button, c3Button, h1Button, h2Button, h3Button;
    private LinearLayout football,cricket, hockey;
    // Track the state of each button
    private boolean isFootballSelected = false;
    private boolean isCricketSelected = false;
    private boolean isHockeySelected = false;
    private boolean isFa = false;
    private boolean isfb = false;
    private boolean isfc = false;
    private boolean isca = false;
    private boolean iscb = false;
    private boolean iscc = false;
    private boolean isha = false;
    private boolean ishb = false;
    private boolean ishc = false;
    private int sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doash_board);

        sportsButton = findViewById(R.id.sports);
        footballButton = findViewById(R.id.football);
        cricketButton = findViewById(R.id.cricket);
        hockeyButton = findViewById(R.id.hockey);
        f1Button = findViewById(R.id.f1);
        f2Button = findViewById(R.id.f2);
        f3Button = findViewById(R.id.f3);
        c1Button = findViewById(R.id.c1);
        c2Button = findViewById(R.id.c2);
        c3Button = findViewById(R.id.c3);
        h1Button = findViewById(R.id.h1);
        h2Button = findViewById(R.id.h2);
        h3Button = findViewById(R.id.h3);
        football=findViewById(R.id.footballLY);
        cricket=findViewById(R.id.cricketLY);
        hockey=findViewById(R.id.hockeyKY);

        sportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  handleSportsButtonClick();
                handleSportsvisible();
            }
        });
        footballButton.setVisibility(View.GONE);  // initially hide football button
        cricketButton.setVisibility(View.GONE);   // initially hide cricket button
        hockeyButton.setVisibility(View.GONE);

        footballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // handleFootballButtonClick();
              //  handleSportsButtonClickfootball();
              //  handleSportsButtonClick();
                handlefootbalvisible();

            }
        });

        cricketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   handleCricketButtonClick();
             //   handleSportsButtonClickcricket();
             //   handleSportsButtonClick();
                handlecricketvisible();
            }
        });

        hockeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   handleHockeyButtonClick();
             //   handleSportsButtonClickhockey();
              //  handleSportsButtonClick();
               handlehockeyvisible();
            }
        });

        f1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFootball_a();
              handleFootballButtonClick();
                handleSportsButtonClickfootball();
               handleSportsButtonClick();
            }
        });

        f2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFootball_b();
                handleFootballButtonClick();
                handleSportsButtonClickfootball();
              handleSportsButtonClick();
            }
        });

        f3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFootball_c();
               handleFootballButtonClick();
                handleSportsButtonClickfootball();
               handleSportsButtonClick();
            }
        });

        c1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCricket_a();
                handleCricketButtonClick();
                handleSportsButtonClickcricket();
                handleSportsButtonClick();
            }
        });

        c2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCricket_b();
                handleCricketButtonClick();
                handleSportsButtonClickcricket();
                handleSportsButtonClick();
            }
        });

        c3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCricket_c();
                handleCricketButtonClick();
                handleSportsButtonClickcricket();
                handleSportsButtonClick();
            }
        });

        h1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleHockey_a();
                handleHockeyButtonClick();
                handleSportsButtonClickhockey();
                handleSportsButtonClick();
            }
        });

        h2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleHockey_b();
                handleHockeyButtonClick();
                handleSportsButtonClickhockey();
               handleSportsButtonClick();
            }
        });

        h3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleHockey_c();
                handleHockeyButtonClick();
                handleSportsButtonClickhockey();
              handleSportsButtonClick();
            }
        });



    }

    private void handleSportsvisible() {
        // Toggle visibility of football, cricket, and hockey buttons
        if (footballButton.getVisibility() == View.VISIBLE) {
            footballButton.setVisibility(View.GONE);
            cricketButton.setVisibility(View.GONE);
            hockeyButton.setVisibility(View.GONE);
            cricket.setVisibility(View.GONE);
            football.setVisibility(View.GONE);
            hockey.setVisibility(View.GONE);
        } else {
            footballButton.setVisibility(View.VISIBLE);
            cricketButton.setVisibility(View.VISIBLE);
            hockeyButton.setVisibility(View.VISIBLE);
        }
    }
    private void handlecricketvisible(){

        if (cricket.getVisibility() == View.VISIBLE) {
           cricket.setVisibility(View.GONE);

          //  cricketButton.setVisibility(View.GONE);
          //  hockeyButton.setVisibility(View.GONE);
        } else {
            cricket.setVisibility(View.VISIBLE);
          //  hockey.setVisibility(View.GONE);
          //  football.setVisibility(View.GONE);

        }
    }

    private void handlefootbalvisible(){
        if (football.getVisibility() == View.VISIBLE) {
            football.setVisibility(View.GONE);
            //  cricketButton.setVisibility(View.GONE);
            //  hockeyButton.setVisibility(View.GONE);
        } else {
            football.setVisibility(View.VISIBLE);
          //  hockey.setVisibility(View.GONE);
           // cricket.setVisibility(View.GONE);

        }
    }
    private void handlehockeyvisible(){

        if (hockey.getVisibility() == View.VISIBLE) {
            hockey.setVisibility(View.GONE);
            //  cricketButton.setVisibility(View.GONE);
            //  hockeyButton.setVisibility(View.GONE);
        } else {
            hockey.setVisibility(View.VISIBLE);
          //  cricket.setVisibility(View.GONE);
         //   football.setVisibility(View.GONE);

        }
    }

    private void handleSportsButtonClickfootball() {

        int selectedCount = 0;

        if (isFa) {
            selectedCount++;
            sum++;
            Toast.makeText(this, "football", Toast.LENGTH_SHORT).show();
        }

        if (isfb) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "cricket", Toast.LENGTH_SHORT).show();
        }

        if (isfc) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "hockey", Toast.LENGTH_SHORT).show();
        }


        if (selectedCount == 0) {


            footballButton.setBackgroundColor(Color.parseColor("#DC0F0F"));
            Toast.makeText(this, "no select", Toast.LENGTH_SHORT).show();
        } else if (selectedCount == 3) {

            footballButton.setBackgroundColor(Color.GREEN);
            Toast.makeText(this, "select all", Toast.LENGTH_SHORT).show();
        } else {

            footballButton.setBackgroundColor(Color.parseColor("#FFA500"));
            Toast.makeText(this, "select any one", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSportsButtonClickcricket() {

        int selectedCount = 0;

        if (isca) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "football", Toast.LENGTH_SHORT).show();
        }

        if (iscb) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "cricket", Toast.LENGTH_SHORT).show();
        }

        if (iscc) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "hockey", Toast.LENGTH_SHORT).show();
        }

        if (selectedCount == 0) {


            cricketButton.setBackgroundColor(Color.parseColor("#DC0F0F"));
            Toast.makeText(this, "no select", Toast.LENGTH_SHORT).show();
        } else if (selectedCount == 3) {

            cricketButton.setBackgroundColor(Color.GREEN);
            Toast.makeText(this, "select all", Toast.LENGTH_SHORT).show();
        } else {

            cricketButton.setBackgroundColor(Color.parseColor("#FFA500"));
            Toast.makeText(this, "select any one", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSportsButtonClickhockey() {
        // Check the state of other buttons and change color accordingly
        int selectedCount = 0;

        if (isha) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "football", Toast.LENGTH_SHORT).show();
        }

        if (ishb) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "cricket", Toast.LENGTH_SHORT).show();
        }

        if (ishc) {

            selectedCount++;
            sum++;
            Toast.makeText(this, "hockey", Toast.LENGTH_SHORT).show();
        }


        if (selectedCount == 0) {


            hockeyButton.setBackgroundColor(Color.parseColor("#DC0F0F"));
            Toast.makeText(this, "no select", Toast.LENGTH_SHORT).show();
        } else if (selectedCount == 3) {

            hockeyButton.setBackgroundColor(Color.GREEN);
            Toast.makeText(this, "select all", Toast.LENGTH_SHORT).show();
        } else {

            hockeyButton.setBackgroundColor(Color.parseColor("#FFA500"));
            Toast.makeText(this, "select any one", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleSportsButtonClick() {

       int selectedCount = 0;

        if (isFa) {
            selectedCount++;
        }

        if (isfb) {
            selectedCount++;
        }

        if (isfc) {
            selectedCount++;
        }

        if (isca) {
            selectedCount++;
        }

        if (iscb) {
            selectedCount++;
        }

        if (iscc) {
            selectedCount++;
        }

        if (isha) {
            selectedCount++;
        }

        if (ishb) {
            selectedCount++;
        }

        if (ishc) {
            selectedCount++;
        }


        if (selectedCount== 0) {

            sportsButton.setBackgroundColor(Color.parseColor("#DC0F0F"));
            Toast.makeText(this, "no select", Toast.LENGTH_SHORT).show();
        } else if (selectedCount==9) {

            sportsButton.setBackgroundColor(Color.GREEN);

            Toast.makeText(this, "select all", Toast.LENGTH_SHORT).show();
        } else {

            sportsButton.setBackgroundColor(Color.parseColor("#FFA500"));

            Toast.makeText(this, "select any one", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFootball_a () {
        isFa = !isFa;
        updateButtonStateg(f1Button, isFa);

    }

    private void handleFootball_b () {
        isfb = !isfb;
        updateButtonStateg(f2Button, isfb);


    }
    private void handleFootball_c () {
        isfc = !isfc;
        updateButtonStateg(f3Button, isfc);


    }

    private void handleCricket_a () {
        isca = !isca;
        updateButtonStateg(c1Button, isca);


    }
    private void handleCricket_b () {
        iscb = !iscb;
        updateButtonStateg(c2Button, iscb);


    }
    private void handleCricket_c () {
        iscc = !iscc;
        updateButtonStateg(c3Button, iscb);

    }

    private void handleHockey_a () {
        isha = !isha;
        updateButtonStateg(h1Button, isha);


    }
    private void handleHockey_b () {
        ishb = !ishb;
        updateButtonStateg(h2Button, ishb);


    }

    private void handleHockey_c () {
        ishc = !ishc;
        updateButtonStateg(h3Button, ishc);


    }


    private void handleFootballButtonClick() {
        isFootballSelected = !isFootballSelected;
        updateButtonState(footballButton, isFootballSelected);


    }

    private void handleCricketButtonClick() {
        isCricketSelected = !isCricketSelected;
        updateButtonState(cricketButton, isCricketSelected);

    }

    private void handleHockeyButtonClick() {
        isHockeySelected = !isHockeySelected;
        updateButtonState(hockeyButton, isHockeySelected);

    }

    private void updateButtonState(Button button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundColor(Color.parseColor("#FFA500"));
        } else {
            button.setBackgroundColor(Color.parseColor("#DC0F0F"));
        }
    }
    private void updateButtonStateg(Button button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundColor(Color.parseColor("#00FF00"));
        } else {
            button.setBackgroundColor(Color.parseColor("#DC0F0F"));
        }
    }


}


