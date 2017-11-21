package com.company.sys.sys.sys.film.generated;

import com.company.sys.sys.sys.film.Film;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.LongField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * The generated base for the {@link
 * com.company.sys.sys.sys.film.Film}-interface representing entities of the
 * {@code film}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedFilm {
    
    /**
     * This Field corresponds to the {@link Film} field that can be obtained
     * using the {@link Film#getId()} method.
     */
    LongField<Film, Long> ID = LongField.create(
        Identifier.ID,
        Film::getId,
        Film::setId,
        TypeMapper.primitive(),
        true
    );
    /**
     * This Field corresponds to the {@link Film} field that can be obtained
     * using the {@link Film#getName()} method.
     */
    StringField<Film, String> NAME = StringField.create(
        Identifier.NAME,
        o -> OptionalUtil.unwrap(o.getName()),
        Film::setName,
        TypeMapper.identity(),
        false
    );
    /**
     * This Field corresponds to the {@link Film} field that can be obtained
     * using the {@link Film#getScore()} method.
     */
    ComparableField<Film, Integer, Integer> SCORE = ComparableField.create(
        Identifier.SCORE,
        o -> OptionalUtil.unwrap(o.getScore()),
        Film::setScore,
        TypeMapper.identity(),
        false
    );
    /**
     * This Field corresponds to the {@link Film} field that can be obtained
     * using the {@link Film#getLevel()} method.
     */
    ComparableField<Film, Integer, Integer> LEVEL = ComparableField.create(
        Identifier.LEVEL,
        o -> OptionalUtil.unwrap(o.getLevel()),
        Film::setLevel,
        TypeMapper.identity(),
        false
    );
    
    /**
     * Returns the id of this Film. The id field corresponds to the database
     * column sys.sys.film.id.
     * 
     * @return the id of this Film
     */
    long getId();
    
    /**
     * Returns the name of this Film. The name field corresponds to the database
     * column sys.sys.film.name.
     * 
     * @return the name of this Film
     */
    Optional<String> getName();
    
    /**
     * Returns the score of this Film. The score field corresponds to the
     * database column sys.sys.film.score.
     * 
     * @return the score of this Film
     */
    OptionalInt getScore();
    
    /**
     * Returns the level of this Film. The level field corresponds to the
     * database column sys.sys.film.level.
     * 
     * @return the level of this Film
     */
    OptionalInt getLevel();
    
    /**
     * Sets the id of this Film. The id field corresponds to the database column
     * sys.sys.film.id.
     * 
     * @param id to set of this Film
     * @return   this Film instance
     */
    Film setId(long id);
    
    /**
     * Sets the name of this Film. The name field corresponds to the database
     * column sys.sys.film.name.
     * 
     * @param name to set of this Film
     * @return     this Film instance
     */
    Film setName(String name);
    
    /**
     * Sets the score of this Film. The score field corresponds to the database
     * column sys.sys.film.score.
     * 
     * @param score to set of this Film
     * @return      this Film instance
     */
    Film setScore(Integer score);
    
    /**
     * Sets the level of this Film. The level field corresponds to the database
     * column sys.sys.film.level.
     * 
     * @param level to set of this Film
     * @return      this Film instance
     */
    Film setLevel(Integer level);
    
    enum Identifier implements ColumnIdentifier<Film> {
        
        ID    ("id"),
        NAME  ("name"),
        SCORE ("score"),
        LEVEL ("level");
        
        private final String columnName;
        private final TableIdentifier<Film> tableIdentifier;
        
        Identifier(String columnName) {
            this.columnName      = columnName;
            this.tableIdentifier = TableIdentifier.of(    getDbmsName(), 
                getSchemaName(), 
                getTableName());
        }
        
        @Override
        public String getDbmsName() {
            return "sys";
        }
        
        @Override
        public String getSchemaName() {
            return "sys";
        }
        
        @Override
        public String getTableName() {
            return "film";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<Film> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}