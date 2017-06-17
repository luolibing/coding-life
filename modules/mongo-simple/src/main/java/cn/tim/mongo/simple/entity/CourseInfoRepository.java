package cn.tim.mongo.simple.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by LuoLiBing on 16/6/15.
 */
public interface CourseInfoRepository extends MongoRepository<CourseInfo, String> {
}
