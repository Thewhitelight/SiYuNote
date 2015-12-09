package cn.libery.siyunote.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.libery.siyunote.Constants;
import cn.libery.siyunote.R;
import cn.libery.siyunote.adapter.NotesAdapter;
import cn.libery.siyunote.db.EventRecord;

public class NoteListFragment extends Fragment {

    private static int NoteTypes;

    @Bind(R.id.notes)
    RecyclerView notes;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private List<EventRecord> records;

    public static NoteListFragment newInstance(int NoteType) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        NoteTypes = NoteType;
        args.putInt(Constants.NOTES_TYPE, NoteType);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (NoteTypes) {
            case 0:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        records = EventRecord.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        notes.setLayoutManager(layoutManager);
        NotesAdapter adapter = new NotesAdapter(records);
        notes.setAdapter(adapter);
        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(NoteDetailActivity.intent(getActivity(), records.get(position).getTimeStamp(), NoteTypes));
            }
        });
        fab.attachToRecyclerView(notes);
    }

    @OnClick(R.id.fab)
    void addNote() {
        startActivity(AddNoteActivity.intent(getActivity(), getArguments().getInt(Constants.NOTES_TYPE)));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
