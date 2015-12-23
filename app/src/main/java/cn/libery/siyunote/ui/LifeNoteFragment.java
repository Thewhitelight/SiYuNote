package cn.libery.siyunote.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.libery.siyunote.Constants;
import cn.libery.siyunote.R;
import cn.libery.siyunote.adapter.NotesAdapter;
import cn.libery.siyunote.db.EventRecord;
import cn.libery.siyunote.otto.BusProvider;
import cn.libery.siyunote.otto.RefreshOtto;

/**
 * Created by Libery on 2015/12/23.
 * Email:libery.szq@qq.com
 */
public class LifeNoteFragment extends Fragment {

    @Bind(R.id.notes)
    RecyclerView notes;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private List<EventRecord> records;
    private NotesAdapter adapter;

    public static LifeNoteFragment newInstance() {
        Bundle args = new Bundle();
        LifeNoteFragment fragment = new LifeNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Subscribe
    public void refreshRecord(RefreshOtto otto) {
        if (otto.ismRefresh()) {
            records.clear();
            records = EventRecord.getAll();
            adapter = new NotesAdapter(records);
            notes.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        records = EventRecord.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        notes.setLayoutManager(layoutManager);
        adapter = new NotesAdapter(records);
        notes.setAdapter(adapter);
        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(NoteDetailActivity.intent(getActivity(), records.get(position).getTimeStamp(), Constants.NOTES_LIFE));
            }
        });
        fab.attachToRecyclerView(notes);
    }

    @OnClick(R.id.fab)
    void addNote() {
        startActivity(AddNoteActivity.intent(getActivity(), getArguments().getInt(Constants.NOTES_TYPE)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

}
