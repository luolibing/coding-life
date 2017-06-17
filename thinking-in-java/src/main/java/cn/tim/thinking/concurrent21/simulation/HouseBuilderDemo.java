package cn.tim.thinking.concurrent21.simulation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/18.
 */
public class HouseBuilderDemo {

    // 房子
    static class House {
        private final int id;

        private boolean
                steel = false, concreteForms = false,
                concreteFoundation = false, plumbing = false,
                concreteSlab = false, framing = false;

        public House(int id) {
            this.id = id;
        }

        public House() {
            this.id = -1;
        }

        public synchronized int getId() {
            return id;
        }

        public synchronized void laySteel() {
            steel = true;
        }

        public synchronized void buildConcreteForms() {
            concreteFoundation = true;
        }

        public synchronized void pourConcreteFoundation() {
            concreteFoundation = true;
        }

        public synchronized void addPlumbing() {
            plumbing = true;
        }

        public synchronized void pourConcreteSlab() {
            concreteSlab = true;
        }

        public synchronized void startFraming() {
            framing = true;
        }

        @Override
        public String toString() {
            return "House{" +
                    "id=" + id +
                    ", steel=" + steel +
                    ", concreteForms=" + concreteForms +
                    ", concreteFoundation=" + concreteFoundation +
                    ", plumbing=" + plumbing +
                    ", concreteSlab=" + concreteSlab +
                    ", framing=" + framing +
                    '}';
        }
    }

    // Queue
    static class HouseQueue extends LinkedBlockingQueue<House> {}

    // 打地基机器
    static class FootingDigger implements Runnable {

        private HouseQueue footingQueue;

        private static int counter = 0;

        public FootingDigger(HouseQueue footingQueue) {
            this.footingQueue = footingQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    House house = new House(counter++);
                    System.out.println("FootingsDigger created " + house);
                    footingQueue.put(house);
                }
            } catch (InterruptedException e) {
                System.out.println("FootingDigger interrupted");
            }
        }
    }

    static class HouseBuilder implements Runnable {

        private HouseQueue footingQueue, finishingQueue;

        private TeamPool teamPool;

        private House house;

        private CyclicBarrier barrier1 = new CyclicBarrier(3);

        private CyclicBarrier barrier2 = new CyclicBarrier(2);

        private boolean secondState = true;

        public HouseBuilder(HouseQueue footingQueue, HouseQueue finishingQueue, TeamPool teamPool) {
            this.footingQueue = footingQueue;
            this.finishingQueue = finishingQueue;
            this.teamPool = teamPool;
        }

        public House hourse() {
            return house;
        }

        public CyclicBarrier barrier() {
            return secondState ? barrier1 : barrier2;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    house = footingQueue.take();
                    teamPool.hire(SteelTeam.class, this);
                    teamPool.hire(ConcreteFormsTeam.class, this);
                    barrier1.await();

                    secondState = false;
                    teamPool.hire(ConcreteFoundationTeam.class, this);
                    barrier2.await();
                    teamPool.hire(PlumbingTeam.class, this);
                    barrier2.await();
                    teamPool.hire(ConcreteSlabTeam.class, this);
                    barrier2.await();
                    teamPool.hire(FramingTeam.class, this);
                    barrier2.await();

                    finishingQueue.put(house);
                    secondState = true;
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting HouseBuilder via interrupt");
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("HouseBuilder off");
        }
    }

    static class Reporter implements Runnable {

        private HouseQueue finishingQueue;

        public Reporter(HouseQueue finishingQueue) {
            this.finishingQueue = finishingQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println(finishingQueue.take());
                }
            } catch (InterruptedException e) {
                System.out.println("Reporter interrupted");
            }
        }
    }

    // 施工队
    static abstract class Team implements Runnable {
        private TeamPool teamPool;

        public Team(TeamPool teamPool) {
            this.teamPool = teamPool;
        }

        protected HouseBuilder houseBuilder;

        public Team assignHouseBuilder(HouseBuilder hb) {
            this.houseBuilder = hb;
            return this;
        }

        private boolean engage = false;

        public synchronized void engage() {
            this.engage = true;
            notifyAll();
        }

        protected abstract void performService();

        @Override
        public void run() {

        }

        // 休息,等待开工命令
        private synchronized void rest() throws InterruptedException {
            engage = false;
            houseBuilder = null;
            teamPool.realse(this);
            while (!engage) {
                wait();
            }
        }

        @Override
        public String toString() {
            return getClass().getName();
        }
    }

    //
    static class SteelTeam extends Team {

        public SteelTeam(TeamPool teamPool) {
            super(teamPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " laying steel");
            houseBuilder.hourse().laySteel();
        }
    }

    static class ConcreteFormsTeam extends Team {
        public ConcreteFormsTeam(TeamPool pool) { super(pool); }
        protected void performService() {
            System.out.println(this + " building concrete forms");
            houseBuilder.hourse().buildConcreteForms();
        }
    }

    static class ConcreteFoundationTeam extends Team {

        public ConcreteFoundationTeam(TeamPool teamPool) {
            super(teamPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " pouring concrete foundation");
            houseBuilder.hourse().pourConcreteFoundation();
        }
    }

    static class PlumbingTeam extends Team {

        public PlumbingTeam(TeamPool teamPool) {
            super(teamPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " add plumbing");
            houseBuilder.hourse().addPlumbing();
        }
    }

    static class ConcreteSlabTeam extends Team {

        public ConcreteSlabTeam(TeamPool teamPool) {
            super(teamPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " pour concrete slab");
            houseBuilder.hourse().pourConcreteSlab();
        }
    }

    static class FramingTeam extends Team {

        public FramingTeam(TeamPool teamPool) {
            super(teamPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " start framing");
            houseBuilder.hourse().startFraming();
        }
    }


    // 工程管理
    static class TeamPool {
        private Set<Team> pool = new HashSet<>();

        public synchronized void add(Team team) {
            pool.add(team);
            notifyAll();
        }

        public synchronized void hire(Class<? extends Team> teamType, HouseBuilder hb) throws InterruptedException {
            for(Team t : pool) {
                if(teamType.isInstance(t)) {
                    pool.remove(t);
                    t.assignHouseBuilder(hb);
                    t.engage();
                    return;
                }
            }
            wait();
            hire(teamType, hb);
        }

        public synchronized void realse(Team team) {
            add(team);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        HouseQueue footingsQueue = new HouseQueue(), finishingQueue = new HouseQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        TeamPool teamPool = new TeamPool();
        exec.execute(new SteelTeam(teamPool));
        exec.execute(new ConcreteFormsTeam(teamPool));
        exec.execute(new ConcreteFoundationTeam(teamPool));
        exec.execute(new PlumbingTeam(teamPool));
        exec.execute(new ConcreteSlabTeam(teamPool));
        exec.execute(new FramingTeam(teamPool));
        exec.execute(new HouseBuilder(
                footingsQueue, finishingQueue, teamPool));
        exec.execute(new Reporter(finishingQueue));
        exec.execute(new FootingDigger(footingsQueue));
        System.in.read();
        exec.shutdownNow();
    }
}
