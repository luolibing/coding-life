package com.company.sys;

import com.company.sys.sys.sys.film.Film;
import com.company.sys.sys.sys.film.FilmImpl;
import com.company.sys.sys.sys.film.FilmManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * orm框架搭配java8 stream
 * https://github.com/speedment/speedment
 *
 * User: luolibing
 * Date: 2017/11/21 16:08
 */
@SpringBootApplication
public class SpeedmentApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpeedmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SysApplication sysApplication = new SysApplicationBuilder()
                .withPassword("root")
                .build();

        // 持久化
        FilmManager films = sysApplication.getOrThrow(FilmManager.class);
        films().forEach(films.persister());

        // select count(*) from film where level>2 and score>60
        long count = films.stream()
                .filter(Film.LEVEL.greaterThan(2))
                .filter(Film.SCORE.greaterThan(60))
                .count();
        System.out.println(count);

        // select * from film
        films.stream()
                .filter(Film.LEVEL.greaterThan(2))
                .filter(Film.SCORE.greaterThan(60))
                .skip(10)
                .limit(10)
                .forEach(System.out::println);

    }


    private List<Film> films() {
        Random random = new Random();
        return IntStream.range(0, 1000).mapToObj( i -> {
            Film film = new FilmImpl();
            film.setName("罗伯特" + System.currentTimeMillis());
            film.setLevel(random.nextInt(10));
            film.setScore(random.nextInt(100));
            return film;
        }).collect(Collectors.toList());


    }
}
