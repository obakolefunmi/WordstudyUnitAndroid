package com.cuwordstudy.solomolaiye.wordstudyunit;



import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.*;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.*;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Comment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class PlaceholderFragment  extends Fragment implements View.OnClickListener ,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int position;

    int topiccount;
    String topictitle, topicword, topicbible;

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_SECTION_NUMBER);

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    String textid;
    private ListView pointlst, announlst, topiclst, questlst,wordcontrilist,  wordquestlist;
    //
    private List<prayerpoints> points;
    private List<announcements> announces;
    private List<topic> topics;
    private List<questions> quests;
    private List<Coment> coments;
    //
    private EditText askquestedit, wordcontriedit;
    private TextView askquestionsend, wordcontrisend;
    //
    private prayerpoints pointsselected = null;
    private announcements announselected = null;
    private topic topicselected = null;
    private questions questselected = null;
    private Coment comentselected = null;


    //
    private ProgressBar pointpgb, announpgb,  topicpgb, questpgb, askquestsendpgb, pgbword, wrdcomtripgb, wordquestpgb;
    //
    private SwipeRefreshLayout refresher, annunrefresher,  topicrefresher, questrefresher, wordrefresher;
    //
    private TextView pointpull, announpull, topicpull, questpull, wordpull, wordword;
    //
    LinearLayout wordcard1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { //views
        View wrd = inflater.inflate(R.layout.fragword, container, false);
        View pp = inflater.inflate(R.layout.ppfrag, container, false);
        View annun = inflater.inflate(R.layout.announfrag, container, false);
        View topic = inflater.inflate(R.layout.topicfraf, container, false);
        View quest = inflater.inflate(R.layout.questfrag, container, false);
        //lists
        pointlst = pp.findViewById(R.id.pointslist);
        announlst = annun.findViewById(R.id.announlist);
        topiclst = topic.findViewById(R.id.topiclist);
        questlst = quest.findViewById(R.id.questlist);
        wordcontrilist = wrd.findViewById(R.id.wordContricontainer);


        //list OnClick
        pointlst.setOnItemClickListener  (this);
        pointlst.setOnItemLongClickListener  (this);

        announlst.setOnItemClickListener  (this);
        announlst.setOnItemLongClickListener  (this);



        topiclst.setOnItemClickListener  (this);
        topiclst.setOnItemLongClickListener  (this);

        questlst.setOnItemClickListener  (this);
        questlst.setOnItemLongClickListener  (this);
        wordcontrilist.setOnItemLongClickListener  (this);


        //pull
        pointpull = pp.findViewById(R.id.pointpull);
        announpull = annun.findViewById(R.id.announpull);
        topicpull = topic.findViewById(R.id.topicpull);
        questpull = quest.findViewById(R.id.questpull);
        wordpull = wrd.findViewById(R.id.wordpull);


        //pgb
        pointpgb = pp.findViewById(R.id.pointpgb);
        announpgb = annun.findViewById(R.id.announpgb);
        topicpgb = topic.findViewById(R.id.topicpgb);
        questpgb = quest.findViewById(R.id.questpgb);
        askquestsendpgb = quest.findViewById(R.id.asqquestsendpgb);
        pgbword = wrd.findViewById(R.id.pgbword);
        wrdcomtripgb = wrd.findViewById(R.id.wordcontripgb);

        //
        askquestedit = quest.findViewById(R.id.askquestionedit);
        wordcontriedit = wrd.findViewById(R.id.wordcontriedit);
        wordcontrisend = wrd.findViewById(R.id.wordcontrisend);
        wordcontrisend.setOnClickListener(this);
        askquestionsend = quest.findViewById(R.id.askquestionsend);
        askquestionsend.setOnClickListener(this);
        //
        wordcard1 = wrd.findViewById(R.id.wowrdholder);


        final TextView wordtitle = wrd.findViewById(R.id.wordTitle);
        wordword = wrd.findViewById(R.id.wordWord);
        final TextView wordbible = wrd.findViewById(R.id.wordBible);
        //


        if (position == 0) {
            new GetWordData(PlaceholderFragment.this, wordtitle, wordword, wordbible).execute(Common.getAddresApitopic());

              wordrefresher = wrd.findViewById(R.id.wordrefresher);


            wordrefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new GetWordData(PlaceholderFragment.this, wordtitle, wordword, wordbible).execute(Common.getAddresApitopic());

                    wordrefresher.setRefreshing(false);
                }
            });

            return wrd;

        }
        else if (position == 1)
        {
            topicrefresher = topic.findViewById(R.id.topicrefresher);
            new GetTopicData(PlaceholderFragment.this).execute(Common.getAddresApitopic());


            topicrefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new GetTopicData(PlaceholderFragment.this).execute(Common.getAddresApitopic());
                    topicrefresher.setRefreshing (false);
                }
            });


            return topic;

        }
        else if (position == 2) {
            new GetQuestData(PlaceholderFragment.this).execute(Common.getAddresApiQuest());

            questrefresher = quest.findViewById (R.id.questrefresher);


            questrefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new GetQuestData(PlaceholderFragment.this).execute(Common.getAddresApiQuest());
                    questrefresher.setRefreshing(false);
                }
            });


            return quest;
        }
        else if (position == 3) {
            new GetAnnounData(PlaceholderFragment.this).execute(Common.getAddresApiAnoun());

            annunrefresher = annun.findViewById  (R.id.announrefresher);


            annunrefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new GetAnnounData(PlaceholderFragment.this).execute(Common.getAddresApiAnoun());
                    annunrefresher.setRefreshing(false);
                }
            });
            return annun;

        }
        else{
            new GetPointData(PlaceholderFragment.this).execute(Common.getAddresApiPoints());
            refresher = pp.findViewById(R.id.refresher);


            refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new GetPointData(PlaceholderFragment.this).execute(Common.getAddresApiPoints());
                    refresher.setRefreshing(false);
                }
            });

            return pp;


        }



    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.askquestionsend: {

                if (!TextUtils.isEmpty(askquestedit.getText().toString().trim())) {
                    new AddQuestion(askquestedit.getText().toString(), PlaceholderFragment.this).execute(Common.getAddresApiQuest());
                } else {
                    askquestedit.setError("Required");
                }
                break;
            }

            case R.id.wordcontrisend: {

                if (!TextUtils.isEmpty(wordcontriedit.getText().toString().trim())) {
                    new AddComent(wordcontriedit.getText().toString(), wordword.getText().toString(),textid, PlaceholderFragment.this).execute(Common.getAddresApiComment());
                } else {
                    wordcontriedit.setError("Required");
                }
                break;
            }

        }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)  {
        if (this.position == 1) {
            topicselected = topics.get(i);
            String title = topicselected.title;
            String bible = topicselected.bible;
            String text = topicselected.text;
            String textid = topicselected._id.getOid();

            Intent gototopic = new Intent(getContext(), TopicActivity.class);
            gototopic.putExtra("topic", title);
            gototopic.putExtra("bible", bible);
            gototopic.putExtra("text", text);
            gototopic.putExtra("textid", textid);

            startActivity(gototopic);


        } else if (this.position == 2) {

            questselected = quests.get(i);
            String title = questselected.title;
            String quest = questselected.question;
            String questid = questselected._id.getOid();

            String user = questselected.user;
            Intent gotoans = new Intent(getContext(), AnswerActivity.class);
            gotoans.putExtra("topic", title);
            gotoans.putExtra("question", quest);
            gotoans.putExtra("user", user);
            gotoans.putExtra("questionid", questid);

            startActivity(gotoans);


        } else if (this.position == 3) {
            announselected = announces.get(i);

        } else {
            pointsselected = points.get(i);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (this.position) {
            case 0: {
                //pop up code here
                //var announsel = view.Id;
                comentselected = coments.get(i);
                android.widget.PopupMenu menu = new android.widget.PopupMenu(getContext(), view);

                // with Android 3 need to use MenuInfater to inflate the menu
                //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);

                // with Android 4 Inflate can be called directly on the menu
                menu.inflate(R.menu.popup);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        //Console.WriteLine("{0} selected", arg1.Item.TitleFormatted);
                        if (menuItem.getItemId() == R.id.delete) {
                            if (MainActivity.currmail.equals("wordstudycu@gmail.com") ||  MainActivity.currname.equals(comentselected.user.toString())) {
                                 Toast.makeText(getContext(), "deleting", Toast.LENGTH_SHORT).show();
                                new DelCommment(comentselected, PlaceholderFragment.this).execute(Common.getAddressSingleComment(comentselected));
                            } else {
                                Toast.makeText(getContext(), "You can't delete this item", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });

                // Android 4 now has the DismissEvent
                menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        String textss = "\"" + textid + "\"";
                        new GetComentSpecifictData(PlaceholderFragment.this).execute(Common.getAddresApiAnsSpecificComment(textss));

                    }
                });

                menu.show();


                return true;
            }
            case 1: {
                //pop up code here
                //var announsel = view.Id;
                android.widget.PopupMenu menu = new android.widget.PopupMenu(getContext(), view);

                // with Android 3 need to use MenuInfater to inflate the menu
                //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);
                topicselected = topics.get(i);
                // with Android 4 Inflate can be called directly on the menu
                menu.inflate(R.menu.popup);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        if (menuItem.getItemId() == R.id.delete) {
                            if (MainActivity.currmail.equals("wordstudycu@gmail.com")) {
                                new DelTopicData(topicselected, PlaceholderFragment.this).execute(Common.getAddressSingleTopic(topicselected));
                            } else {
                                Toast.makeText(getContext(), "You can't delete this item", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });

                // Android 4 now has the DismissEvent
                menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        new GetTopicData(PlaceholderFragment.this).execute(Common.getAddresApitopic());
                    }
                });
                menu.show();


                return true;
            }
            case 2: {
                //pop up code here
                //var announsel = view.Id;
                android.widget.PopupMenu menu = new android.widget.PopupMenu(getContext(), view);

                // with Android 3 need to use MenuInfater to inflate the menu
                //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);
                questselected = quests.get(i);


                // with Android 4 Inflate can be called directly on the menu
                menu.inflate(R.menu.popup);


                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.delete) {
                            if (MainActivity.currmail.equals("wordstudycu@gmail.com") ||  MainActivity.currname.equals(questselected.user.toString())) {
                                new DelQuestionData(questselected, PlaceholderFragment.this).execute(Common.getAddressSingleQuest(questselected));
                            } else {
                                Toast.makeText(getContext(), "You can't delete this item", Toast.LENGTH_SHORT).show();
                            }
                        }return true;
                    }
                });

                // Android 4 now has the DismissEvent
                menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        new GetQuestData(PlaceholderFragment.this).execute(Common.getAddresApiQuest());

                    }
                });

                menu.show();


                return true;
            }
            case 3: {
                //pop up code here
                //var announsel = view.Id;
                android.widget.PopupMenu menu = new android.widget.PopupMenu(getContext(), view);

                // with Android 3 need to use MenuInfater to inflate the menu
                //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);
                announselected = announces.get(i);
                // with Android 4 Inflate can be called directly on the menu
                menu.inflate(R.menu.popup);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.delete) {
                            if (MainActivity.currmail.equals("wordstudycu@gmail.com")) {
                                new DelAnnounData(announselected, PlaceholderFragment.this).execute(Common.getAddressSingleAnoun(announselected));
                            } else {
                                Toast.makeText(getContext(), "You can't delete this item", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });

                // Android 4 now has the DismissEvent
                menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        new GetAnnounData(PlaceholderFragment.this).execute(Common.getAddresApiAnoun());

                    }
                });
                menu.show();

                return true;
            }
            case 4: { //pop up code here
                //var announsel = view.Id;
                android.widget.PopupMenu menu = new android.widget.PopupMenu(getContext(), view);

                // with Android 3 need to use MenuInfater to inflate the menu
                //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);
                pointsselected = points.get(i);

                // with Android 4 Inflate can be called directly on the menu
                menu.inflate(R.menu.popup);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.delete) {
                            if (MainActivity.currmail.equals("wordstudycu@gmail.com")) {
                                new DelPrayerpointData(pointsselected, PlaceholderFragment.this).execute(Common.getAddressSinglePoints(pointsselected));
                            } else {
                                Toast.makeText(getContext(), "You can't delete this item", Toast.LENGTH_SHORT).show();
                            }
                        } return true;
                    }
                });

                // Android 4 now has the DismissEvent
                menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        new GetPointData(PlaceholderFragment.this).execute(Common.getAddresApiPoints());

                    }
                });

                menu.show();

                return true;
            }
            default:
                return true;
        }
    }


    //Prayer Points
    public class GetPointData extends AsyncTask<String, java.lang.Void, String> {
      //  private ProgressDialog progressBar = new ProgressDialog(getContext());
        private PlaceholderFragment activity;

        public GetPointData(PlaceholderFragment activity) {
            this.activity = activity;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.pointpgb.setVisibility(View.VISIBLE);
            activity.pointlst.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
            try {
                Type listtype = new TypeToken<List<prayerpoints>>(){}.getType();
                activity.points = new Gson().fromJson(s, listtype);
                Collections.reverse(activity.points);
                PointAdapter adapter = new PointAdapter(getContext(), activity.points);
                activity.pointlst.setAdapter(adapter);
                activity.pointpgb.setVisibility(View.GONE) ;
                activity.pointpull.setVisibility(View.GONE) ;
                activity.pointlst.setVisibility(View.VISIBLE) ;

            } catch (Exception ex) {
                activity.pointpull.setVisibility(View.VISIBLE);
                activity.pointpull.setVisibility(View.VISIBLE) ;

            }}
            else {
                activity.pointpull.setVisibility(View.VISIBLE);
                activity.pointpull.setVisibility(View.VISIBLE) ;
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;        }

    }

    public class DelPrayerpointData extends AsyncTask<String, String, String> {
        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();
        prayerpoints pp = null;

        public DelPrayerpointData(prayerpoints pp, PlaceholderFragment activity) {
            this.activity = activity;
            this.pp = pp;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.pointpgb.setVisibility(View.VISIBLE);
            activity.pointlst.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.pointpgb.setVisibility(View.GONE)  ;
            activity.pointlst.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            String json = new Gson().toJson(pp);
            http.DeleteHttpData(url, json);
            return "";
        }


    }

    //Announcement
    public class GetAnnounData extends AsyncTask<String, java.lang.Void, String> {
        //    private ProgressDialog progressBar = new ProgressDialog(getContext());
        private PlaceholderFragment activity;

        public GetAnnounData(PlaceholderFragment activity) {
            this.activity = activity;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.announpgb.setVisibility(View.VISIBLE);
            activity.announlst.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
            try {
                Type listtype = new TypeToken<List<announcements>>(){}.getType();
                activity.announces = new Gson().fromJson(s, listtype);
                Collections.reverse(activity.announces);
                AnnounAdapter adapter = new AnnounAdapter(getContext(), activity.announces);
                activity.announlst.setAdapter(adapter);
                activity.announpgb.setVisibility(View.GONE);
                activity.announpull.setVisibility(View.GONE);
                activity.announlst.setVisibility(View.VISIBLE) ;

            } catch (Exception ex) {
                activity.announpull.setVisibility(View.VISIBLE) ;
            }}else
                activity.announpull.setVisibility(View.VISIBLE) ;

        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;
        }

    }

    public class EditAnnounData extends AsyncTask<String, java.lang.Void, String> {
        String newannoun = "";
        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();
        announcements announselected = null;

        public EditAnnounData(announcements announselected, String newannoun, PlaceholderFragment activity) {
            this.newannoun = newannoun;
            this.activity = activity;
            this.announselected = announselected;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.announpgb.setVisibility(View.VISIBLE) ;
            activity.announlst.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            activity.announpgb.setVisibility(View.GONE);
            activity.announlst.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            announselected.announcement = newannoun;
            String json = new Gson().toJson(announselected);
            http.PutHttpData(url, json);
            return "";        }


    }

    public class DelAnnounData extends AsyncTask<String, String, String> {
        String newannoun = "";
        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();
        announcements announselected = null;

        public DelAnnounData(announcements announselected, PlaceholderFragment activity) {

            this.activity = activity;
            this.announselected = announselected;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.announpgb.setVisibility(View.VISIBLE) ;
            activity.announlst.setVisibility(View.GONE) ;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.announpgb.setVisibility(View.GONE) ;
            activity.announlst.setVisibility(View.VISIBLE) ;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            announselected.announcement = newannoun;
            String json = new Gson().toJson(announselected);
            http.DeleteHttpData(url, json);
            return "";
        }




    }

    //prayer Requests


    //Topic
    public class GetTopicData extends AsyncTask<String, java.lang.Void, String> {

        private PlaceholderFragment activity;

        public GetTopicData(PlaceholderFragment activity) {
            this.activity = activity;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.topicpgb.setVisibility(View.VISIBLE);
            activity.topiclst.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {

                try {
                Type listtype = new TypeToken<List<topic>>(){}.getType();
                activity.topics = new Gson().fromJson(s, listtype);
                Collections.reverse(activity.topics);
                int i = new Random().nextInt(activity.topiccount + 1)+1;
                activity.topictitle = activity.topics.get(i).title;
                activity.topicword = activity.topics.get(i).text;
                activity.topicbible = activity.topics.get(i).bible;

                TopicAdapter adapter = new TopicAdapter(getContext(), activity.topics);
                activity.topiclst.setAdapter(adapter);
                activity.topiccount = activity.topics.size();

                activity.topicpgb.setVisibility(View.GONE) ;
                activity.topicpull.setVisibility(View.GONE) ;
                activity.topiclst.setVisibility(View.VISIBLE) ;

            } catch (Exception ex) {
                activity.topicpull.setVisibility(View.VISIBLE);
            }
            }else
                activity.topicpull.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;
        }

    }

    public class DelTopicData extends AsyncTask<String, String, String> {
        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();
        topic topicselected = null;

        public DelTopicData(topic topicselected, PlaceholderFragment activity) {
            this.activity = activity;
            this.topicselected = topicselected;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.topicpgb.setVisibility(View.VISIBLE) ;
            activity.topiclst.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.topicpgb.setVisibility(View.GONE);
            activity.topiclst.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            String json = new Gson().toJson(topicselected);
            http.DeleteHttpData(url, json);
            return "";
        }





    }

    //Word
    public class GetWordData extends AsyncTask<String, java.lang.Void, String> {

        private PlaceholderFragment activity;
        TextView title, text, bible;

        public GetWordData(PlaceholderFragment activity, TextView title, TextView text, TextView bible) {
            this.activity = activity;
            this.title = title;
            this.text = text;
            this.bible = bible;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.pgbword.setVisibility(View.VISIBLE) ;
            activity.wordcard1.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {

                try {
                    List<topic> topics;

                    Type listtype = new TypeToken<List<topic>>() {
                    }.getType();
                    topics = new Gson().fromJson(s, listtype);
                    int topiccount = topics.size();

                    Random rand = new Random();

                    int i = rand.nextInt(topiccount) + 0;

                    title.setText(topics.get(i).title);
                    text.setText(topics.get(i).text);
                    String topic = topics.get(i).title;
                    String texts = topics.get(i).text;
                    textid = topics.get(i)._id.getOid();
                    bible.setText(topics.get(i).bible);
                    activity.pgbword.setVisibility(View.GONE);
                    activity.wordcard1.setVisibility(View.VISIBLE);
                    String textss = "\"" + textid + "\"";

                    new GetComentSpecifictData(activity).execute(Common.getAddresApiAnsSpecificComment(textss));


                    activity.wordpull.setVisibility(View.GONE);

                } catch (Exception ex) {
                    activity.wordpull.setVisibility(View.VISIBLE);
                }
            }else
                activity.wordpull.setVisibility(View.VISIBLE);



        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;
        }

    }

    public class AddComent extends AsyncTask<String, java.lang.Void, String> {
        String comment = "";
        String text = "";
        String textid = "";

        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();

        public AddComent(String comment, String text,String textid ,PlaceholderFragment activity) {
            this.text = text;
            this.textid = textid;

            this.comment = comment;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.wrdcomtripgb.setVisibility(View.VISIBLE);
            activity.wordcontriedit.setVisibility(View.GONE);
            activity.wordcontrisend.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            activity.wrdcomtripgb.setVisibility(View.GONE);
            activity.wordcontriedit.setVisibility(View.VISIBLE) ;
            activity.wordcontrisend.setVisibility(View.VISIBLE) ;
            activity.wordcontriedit.setText("");
            String textss = "\"" + textid + "\"";

            new GetComentSpecifictData(activity).execute(Common.getAddresApiAnsSpecificComment(textss));

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            Coment comments = new Coment();
            comments.contribution = comment;
            comments.user = user;
            comments.textid = textid;
            String json = new Gson().toJson(comments);
            http.PostHttpData(url, json);
            return "";
        }
    }


    private class GetComentSpecifictData extends AsyncTask<String, java.lang.Void, String> {

        private PlaceholderFragment activity;
        List<Coment> coments;

        public GetComentSpecifictData(PlaceholderFragment activity) {
            this.activity = activity;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.wrdcomtripgb.setVisibility(View.VISIBLE) ;
            activity.wordcontrilist.setVisibility(View.GONE) ;

            

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                 {
                    Type listtype = new TypeToken<List<Coment>>() {}.getType();
                    activity.coments = new Gson().fromJson(s, listtype);
                    Collections.reverse(activity.coments);
                   CommentAdapter adapter = new CommentAdapter(getContext(), activity.coments);
                    activity.wordcontrilist.setAdapter(adapter);

                    activity.wrdcomtripgb.setVisibility(View.GONE);
                    // activity.topicacctpull.setVisibility(View.GONE) = ViewStates.Gone;
                    activity.wordcontrilist.setVisibility(View.VISIBLE);
                }
            } catch (Exception ex) {
                //activity.topicacctpull.setVisibility(View.VISIBLE) = ViewStates.Visible;
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;
        }





    }

    public class DelCommment extends AsyncTask<String, String, String> {
        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();
        Coment comentselected = null;

        public DelCommment(Coment comentselected, PlaceholderFragment activity) {
            this.activity = activity;
            this.comentselected = comentselected;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.wrdcomtripgb.setVisibility(View.VISIBLE) ;
            activity.wordcontrilist.setVisibility(View.GONE) ;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.wrdcomtripgb.setVisibility(View.GONE);
            activity.wordcontrilist.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            String json = new Gson().toJson(comentselected);
            http.DeleteHttpData(url, json);
            return "";

        }


    }

    // Questions
    public class GetQuestData extends AsyncTask<String, java.lang.Void, String> {

        private PlaceholderFragment activity;

        public GetQuestData(PlaceholderFragment activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.questpgb.setVisibility(View.VISIBLE) ;
            activity.questlst.setVisibility(View.VISIBLE);

            activity.askquestedit.setVisibility(View.VISIBLE) ;
            activity.askquestionsend.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){

                try {
                Type listtype = new TypeToken<List<questions>>(){}.getType();
                activity.quests = new Gson().fromJson(s, listtype);
                Collections.reverse(activity.quests);
                QuestionAdapter adapter = new QuestionAdapter(getContext(), activity.quests);
                activity.questlst.setAdapter(adapter);
                activity.questpgb.setVisibility(View.GONE);
                activity.questpull.setVisibility(View.GONE);
                activity.questlst.setVisibility(View.VISIBLE);
                activity.askquestedit.setVisibility(View.VISIBLE) ;
                activity.askquestionsend.setVisibility(View.VISIBLE);
            } catch (Exception ex) {
                activity.questpull.setVisibility(View.VISIBLE);
            }
            }else
                activity.questpull.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;
        }

    }

    public class AddQuestion extends AsyncTask<String, java.lang.Void, String> {
        String question = "";
        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();

        public AddQuestion(String question, PlaceholderFragment activity) {
            this.question = question;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.askquestsendpgb.setVisibility(View.VISIBLE);
            activity.askquestedit.setVisibility(View.GONE) ;
            activity.askquestionsend.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.askquestsendpgb.setVisibility(View.GONE) ;
            activity.askquestedit.setVisibility(View.VISIBLE) ;
            activity.askquestionsend.setVisibility(View.VISIBLE) ;
            activity.askquestedit.setText("");
            new GetQuestData(activity).execute(Common.getAddresApiQuest());
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            questions questss = new questions();
            questss.question = question;
            questss.user = user;
            questss.title = "";
            questss.titleid = "";

            String json = new Gson().toJson(questss);
            http.PostHttpData(url, json);
            return "";
        }


    }

    public class DelQuestionData extends AsyncTask<String, String, String> {
        String user = MainActivity.currname;
        PlaceholderFragment activity = new PlaceholderFragment();
        questions announselected = null;


        public DelQuestionData(questions announselected, PlaceholderFragment activity) {
            this.activity = activity;
            this.announselected = announselected;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.questpgb.setVisibility(View.VISIBLE);
            activity.questlst.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.questpgb.setVisibility(View.GONE) ;
            activity.questlst.setVisibility(View.VISIBLE) ;
        }

        @Override
        protected String doInBackground(String... strings) {

            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            String json = new Gson().toJson(announselected);
            http.DeleteHttpData(url, json);
            return "";
        }


    }





}
