package de.fh_muenster.projectxx;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ForumPosts extends ActionBarActivity {

    private ArrayList<String> sPosts = new ArrayList<String>();
    private ArrayList<Post> posts = new ArrayList<Post>();
    private ListView lvPost ;
    private EditText postMsg;
    private Forum forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ii = getIntent();
        forum = (Forum)ii.getSerializableExtra("forum");
        posts = forum.getPosts();
        setContentView(R.layout.activity_forum_posts);


        //Frage als überschrift drucken.
        TextView textViewToChange = (TextView) findViewById(R.id.Forumtitle);
        textViewToChange.setText(forum.getTitle());

        //Button hinzufügen und Logik hinterlegen
        postMsg = (EditText) findViewById(R.id.edtPostForum);
        final Button addBtn = (Button) findViewById(R.id.btnposten);

        postMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!postMsg.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Post wurde gespeichert", Toast.LENGTH_SHORT).show();
                addPost(postMsg.getText().toString());
                postMsg.setText("");

            }
        });

        // Get ListView object from xml
        lvPost = (ListView) findViewById(R.id.lvPosts);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, sPosts);

        // Assign adapter to ListView
        lvPost.setAdapter(adapter);
        validatPost();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forum_posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addPost(String post){
        forum.savePost(post);
        validatPost();

    }

    private void validatPost(){
        this.posts = forum.getPosts();

        //String Arraylist neu aufbauen
        sPosts.clear();
        for(Post tmp : this.posts) {
            sPosts.add(tmp.getPost());
        }

    }
}
