package cn.libery.siyunote.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.libery.siyunote.Constants;
import cn.libery.siyunote.R;
import cn.libery.siyunote.adapter.NotesAdapter;
import cn.libery.siyunote.db.EventRecord;
import cn.libery.siyunote.otto.BusProvider;
import cn.libery.siyunote.otto.ListTypeOtto;
import cn.libery.siyunote.otto.RefreshOtto;
import cn.libery.siyunote.utils.AppUtils;

import static butterknife.ButterKnife.bind;
import static butterknife.ButterKnife.unbind;

/**
 * Created by Libery on 2015/12/23.
 * Email:libery.szq@qq.com
 */
public class WorkNoteFragment extends Fragment {


    @Bind(R.id.notes)
    RecyclerView notes;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private List<EventRecord> records;
    private NotesAdapter adapter;

    public static WorkNoteFragment newInstance() {
        Bundle args = new Bundle();
        WorkNoteFragment fragment = new WorkNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @OnClick(R.id.fab)
    void addNote() {
        startActivity(AddNoteActivity.intent(getActivity(), Constants.NOTES_WORK));
    }

    @Subscribe
    public void refreshRecord(RefreshOtto otto) {
        if (otto != null && otto.ismRefresh() && records != null && adapter != null && notes != null) {
            records.clear();
            records = EventRecord.getWorkNotes();
            adapter.setRecords(records);
            notes.setAdapter(adapter);
        }
    }

    @Subscribe
    public void setLayoutManager(ListTypeOtto otto) {
        if (otto != null) {
            setListType(otto.getType().equals(Constants.LIST_LINEAR));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        records = EventRecord.getWorkNotes();
        setListType(AppUtils.isListLinear());
        adapter = new NotesAdapter();
        adapter.setRecords(records);
        notes.setAdapter(adapter);
        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(NoteDetailActivity.intent(getActivity(), records.get(position).getTimeStamp(), Constants.NOTES_WORK));
            }
        });
        fab.attachToRecyclerView(notes);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    private void setListType(boolean type) {
        if (notes != null) {
            if (type) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                notes.setLayoutManager(linearLayoutManager);
            } else {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                notes.setLayoutManager(gridLayoutManager);
            }
        }
    }
}
