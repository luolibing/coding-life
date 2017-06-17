package cn.design.patterns.mvc.dj;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public class BeatModel implements BeatModelInterface, MetaEventListener {

    // 音序器
    Sequencer sequencer;
    // 打击器
    List<BeatObserver> beatObservers = new ArrayList<>();
    //
    List<BPMObserver> bpmObservers = new ArrayList<>();
    // 每秒拍数
    int bpm = 90;
    // 序列
    Sequence sequence;
    // 音轨
    Track track;

    @Override
    public void initialize() {
        setUpMidi();
        buildTrackAndStart();
    }

    @Override
    public void on() {
        System.out.println("starting the sequencer");
        sequencer.start();
        setBPM(90);
    }

    @Override
    public void off() {
        setBPM(0);
        sequencer.stop();
    }

    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(getBPM());
        notifyBPMObservers();
    }

    @Override
    public int getBPM() {
        return bpm;
    }

    @Override
    public void registerObserver(BeatObserver observer) {
        beatObservers.add(observer);
    }

    @Override
    public void removeObserver(BeatObserver observer) {
        int index = beatObservers.indexOf(observer);
        if(index >=0 ) {
            beatObservers.remove(observer);
        }
    }

    @Override
    public void registerObserver(BPMObserver observer) {
        bpmObservers.add(observer);
    }

    @Override
    public void removeObserver(BPMObserver observer) {
        int index = bpmObservers.indexOf(observer);
        if(index >=0 ) {
            bpmObservers.remove(observer);
        }
    }

    @Override
    public void meta(MetaMessage message) {
        if(message.getType() == 47) {
            beatEvent();
            sequencer.start();
            setBPM(getBPM());
        }
    }

    void beatEvent() {
        notifyBeatObservers();
    }

    public void setUpMidi() {
        try {
            //
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            // 序列
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBPM());
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyBPMObservers() {
        for(int i = 0; i< bpmObservers.size(); i++) {
            BPMObserver bpmObserver = bpmObservers.get(i);
            bpmObserver.updateBPM();
        }
    }

    public void notifyBeatObservers() {
        for(int i = 0; i < beatObservers.size(); i++) {
            BeatObserver observer = beatObservers.get(i);
            observer.updateBeat();
        }
    }

    public void buildTrackAndStart() {
        int[] trackList = {35, 0, 46, 0};

        sequence.deleteTrack(null);
        track = sequence.createTrack();

        makeTracks(trackList);
        track.add(makeEvent(192,9,1,0,4));
        try {
            sequencer.setSequence(sequence);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void makeTracks(int[] list) {

        for (int i = 0; i < list.length; i++) {
            int key = list[i];

            if (key != 0) {
                track.add(makeEvent(144,9,key, 100, i));
                track.add(makeEvent(128,9,key, 100, i+1));
            }
        }
    }

    public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return event;
    }
}
