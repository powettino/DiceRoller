package yeapp.com.diceroll;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import java.util.Random;


public class DiceRollerMain extends ActionBarActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    LinearLayout vista;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ((Button) findViewById(R.id.faces)).setText(savedInstanceState.getCharSequence("faces"));
        ((Button) findViewById(R.id.dices)).setText(savedInstanceState.getCharSequence("dices"));
        int[] array = savedInstanceState.getIntArray("draw");
        for(int i=0;i<array.length;i++){
            ImageView img = new ImageView(this);
            registerForContextMenu(img);
            switch (array[i]) {
                case 1: {
                    img.setImageResource(R.drawable.face_1);
                    vista.addView(img);
                    img.setTag(1);
                    break;
                }
                case 2: {
                    img.setImageResource(R.drawable.face_2);
                    vista.addView(img);
                    img.setTag(2);
                    break;
                }
                case 3: {
                    img.setImageResource(R.drawable.face_3);
                    vista.addView(img);
                    img.setTag(3);
                    break;
                }
                case 4: {
                    img.setImageResource(R.drawable.face_4);
                    vista.addView(img);
                    img.setTag(4);
                    break;
                }
                case 5: {
                    img.setImageResource(R.drawable.face_5);
                    vista.addView(img);
                    img.setTag(5);
                    break;
                }
                case 6: {
                    img.setImageResource(R.drawable.face_6);
                    vista.addView(img);
                    img.setTag(6);
                    break;
                }
                default: {
                    break;
                }
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence("faces", ((Button) findViewById(R.id.faces)).getText());
        outState.putCharSequence("dices", ((Button) findViewById(R.id.dices)).getText());
        int[] draw = new int[vista.getChildCount()];

        for (int i = 0; i < vista.getChildCount(); i++) {
            ImageView temp = (ImageView) vista.getChildAt(i);
            draw[i] = (Integer) temp.getTag();
        }
        outState.putIntArray("draw", draw);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roller_main);

        findViewById(R.id.faces).setOnClickListener(this);
        findViewById(R.id.dices).setOnClickListener(this);
        findViewById(R.id.roll).setOnClickListener(this);
        vista = (LinearLayout) findViewById(R.id.linear);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dice_roller_main, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().toString().contains("dado")) {
            int opt = item.getItemId();
            int dado = item.getGroupId();
            ImageView img = (ImageView) vista.getChildAt(dado - 1);
            if (img != null) {
                if (opt == 2) {
                    vista.removeView(img);
                    return true;
                } else if (opt == 1) {
                    int faces = Integer.parseInt((String) ((Button) findViewById(R.id.faces)).getText());
                    switch (calcola(faces)) {
                        case 1: {
                            img.setImageResource(R.drawable.face_1);
                            img.setTag(1);
                            break;
                        }
                        case 2: {
                            img.setImageResource(R.drawable.face_2);
                            img.setTag(2);
                            break;
                        }
                        case 3: {
                            img.setImageResource(R.drawable.face_3);
                            img.setTag(3);
                            break;
                        }
                        case 4: {
                            img.setImageResource(R.drawable.face_4);
                            img.setTag(4);
                            break;
                        }
                        case 5: {
                            img.setImageResource(R.drawable.face_5);
                            img.setTag(5);
                            break;
                        }
                        case 6: {
                            img.setImageResource(R.drawable.face_6);
                            img.setTag(6);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    return true;
                }
            }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        if (v instanceof ImageView) {
            for (int i = 0; i < vista.getChildCount(); i++) {
                if (vista.getChildAt(i) == v) {
                    menu.setHeaderTitle("Opzioni");
                    int numberDice = i + 1;
                    menu.add(numberDice, 1, numberDice, "Rolla dado " + numberDice);
                    menu.add(numberDice, 2, numberDice, "Elimina dado " + numberDice);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reset) {
            ((Button) findViewById(R.id.faces)).setText("6");
            ((Button) findViewById(R.id.dices)).setText("2");
            vista.removeAllViews();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        PopupMenu pop = new PopupMenu(this, v);
        pop.setOnMenuItemClickListener(this);
        pop.getMenu().clear();

        switch (v.getId()) {
            case R.id.roll: {

                int[] res = calcolaAll();
                vista.removeAllViews();
                for (int i = 0; i < res.length; i++) {
                    ImageView img = new ImageView(this);
                    registerForContextMenu(img);
                    switch (res[i]) {
                        case 1: {
                            img.setImageResource(R.drawable.face_1);
                            vista.addView(img);
                            img.setTag(1);
                            break;
                        }
                        case 2: {
                            img.setImageResource(R.drawable.face_2);
                            vista.addView(img);
                            img.setTag(2);
                            break;
                        }
                        case 3: {
                            img.setImageResource(R.drawable.face_3);
                            vista.addView(img);
                            img.setTag(3);
                            break;
                        }
                        case 4: {
                            img.setImageResource(R.drawable.face_4);
                            vista.addView(img);
                            img.setTag(4);
                            break;
                        }
                        case 5: {
                            img.setImageResource(R.drawable.face_5);
                            vista.addView(img);
                            img.setTag(5);
                            break;
                        }
                        case 6: {
                            img.setImageResource(R.drawable.face_6);
                            vista.addView(img);
                            img.setTag(6);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                break;
            }
            case R.id.dices: {
                pop.getMenu().add(1, 1, 1, "1");
                pop.getMenu().add(1, 2, 2, "2");
                pop.getMenu().add(1, 3, 3, "3");
                pop.show();
                break;
            }
            case R.id.faces: {
                pop.getMenu().add(2, 1, 1, "1");
                pop.getMenu().add(2, 2, 2, "2");
                pop.getMenu().add(2, 3, 3, "3");
                pop.getMenu().add(2, 4, 4, "4");
                pop.getMenu().add(2, 5, 5, "5");
                pop.getMenu().add(2, 6, 6, "6");
                pop.show();
                break;
            }
            default:
                break;
        }
    }

    private int[] calcolaAll() {
        int faces = Integer.parseInt((String) ((Button) findViewById(R.id.faces)).getText());
        int dices = Integer.parseInt((String) ((Button) findViewById(R.id.dices)).getText());
        int[] res = new int[dices];
        for (int i = 0; i < dices; i++) {
            res[i] = calcola(faces);
        }
        return res;
    }

    private int calcola(int faces) {
        Random random = new Random();
        return random.nextInt(faces) + 1;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getGroupId()) {
            case 1: {
                ((Button) findViewById(R.id.dices)).setText(item.getTitle());
                return true;
            }
            case 2: {
                ((Button) findViewById(R.id.faces)).setText(item.getTitle());
                return true;
            }
            default: {
                break;
            }
        }
        return false;
    }
}
