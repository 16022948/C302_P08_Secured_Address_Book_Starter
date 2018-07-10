package sg.edu.rp.webservices.c302_p08_secured_address_book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;
    private ArrayList<Contact> alContact;
    private ArrayAdapter<Contact> aaContact;

    // TODO (3) Declare loginId and apikey
    private String loginId, apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (ListView) findViewById(R.id.listViewContact);
        alContact = new ArrayList<Contact>();

        aaContact = new ContactAdapter(this, R.layout.contact_row, alContact);
        lvContact.setAdapter(aaContact);

        // TODO (4) Get loginId and apikey from the previous Intent
        Intent intent = getIntent();
        loginId = intent.getStringExtra("loginId");
        apikey = intent.getStringExtra("apikey");

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact selectedContact = alContact.get(position);

                // TODO (7) When a contact is selected, create an Intent to View Contact Details
                // Put the following into intent:- contact_id, loginId, apikey

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        alContact.clear();

        // TODO (5) Refresh the main activity with the latest list of contacts by calling getListOfContacts.php
        // What is the web service URL?
        String url = "http://10.0.2.2/C302_P08_SecuredCloudAddressBook/getListOfContacts.php";
        HttpRequest request = new HttpRequest(url);
        // What is the HTTP method?
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("POST");
        // What parameters need to be provided?
        request.addData("loginId", loginId);
        request.addData("apikey", apikey);

        request.execute();
    }

    // TODO (6) In the HttpResponseListener for getListOfContacts.php, get all contacts from the results and show in the list
    private HttpRequest.OnHttpResponseListener mHttpResponseListener = new HttpRequest.OnHttpResponseListener() {
        @Override
        public void onResponse(String response) {
            try {
                Log.i("JSON Results: ", response);

                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    int contactId = object.getInt("id");
                    String firstName = object.getString("firstname");
                    String lastName = object.getString("lastname");
                    String mobile = object.getString("mobile");

                    Contact contact = new Contact(contactId, firstName, lastName, mobile);
                    alContact.add(contact);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            aaContact.notifyDataSetChanged();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_add) {

            // TODO (8) Create an Intent to Create Contact
            // Put the following into intent:- loginId, apikey

        }
        return super.onOptionsItemSelected(item);
    }
}
