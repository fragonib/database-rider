package com.github.database.rider.core.api.configuration;

import com.github.database.rider.core.connection.RiderDataSource;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import com.github.database.rider.core.replacers.Replacer;

import org.dbunit.database.IMetadataHandler;
import org.dbunit.dataset.datatype.IDataTypeFactory;

import java.lang.annotation.*;

/**
 * Created by rafael-pestano on 30/08/2016. This annotation configures DBUnit properties
 * (http://dbunit.sourceforge.net/properties.html) for a given dataset executor. It can be used at class or method
 * level.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DBUnit {

    /**
     * @return executor id for which the properties will be setup.
     */
    String executor() default DataSetExecutorImpl.DEFAULT_EXECUTOR_ID;

    /**
     * @return if true database connection will be reused among tests otherwise the connection will be closed after each test
     */
    boolean cacheConnection() default true;

    boolean cacheTableNames() default true;

    /**
     * @return if true will activate connection leak detection.
     */
    boolean leakHunter() default false;

    /**
     * @return value which configures DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES. Defaults to false.
     */
    boolean qualifiedTableNames() default false;

    /**
     * @return value which configures DatabaseConfig.FEATURE_BATCHED_STATEMENTS
     */
    boolean batchedStatements() default false;

    /**
     * Enable or disable case sensitive table names. If enabled, Dbunit handles all table names in a case sensitive way.
     *
     * @since 0.15.0
     * @return boolean value which configures case-sensitive table names (also columns)
     */
    boolean caseSensitiveTableNames() default false;

    /**
     * If enabled an exception will be raised when <code>cleanBefore</code> or <code>cleanAfter</code> fails.
     * If disabled then only a warn message is logged. Default is <code>false</code>
     *
     * @since 1.16.0
     * @return boolean value which configures case-sensitive table names (also columns)
     */
    boolean raiseExceptionOnCleanUp() default false;

    /**
     * If enabled then the sequenceFiltering will be disabled even if it is enabled at <code>@DataSet</code> level
     * @since 1.20.0
     * @return boolean indicating the sequence filtering should be disabled or not.
     */
    boolean disableSequenceFiltering() default false;

    /**
     * If enabled then cleanBefore will be enabled even if it is disabled at <code>@DataSet</code> level
     * @since 1.30.0
     * @return boolean indicating cleanBefore should always be performed or not.
     */
    boolean alwaysCleanBefore() default false;

    /**
     * If enabled then cleanAfter will be enabled even if it is disabled at <code>@DataSet</code> level
     * @since 1.30.0
     * @return boolean indicating cleanAfter should always be performed or not.
     */
    boolean alwaysCleanAfter() default false;

    /**
     * In the process of initialization, if the actual database type is different from the expected database type,
     * exception will be thrown unless the expected database type is {@link RiderDataSource.DBType#UNKNOWN}.
     * <p>
     * Default is {@link RiderDataSource.DBType#UNKNOWN}.
     *
     * @return the expected database type.
     * @since 1.16.0
     */
    RiderDataSource.DBType expectedDbType() default RiderDataSource.DBType.UNKNOWN;

    /**
     * @return value which configures DatabaseConfig.PROPERTY_DATATYPE_FACTORY
     */
    Class<? extends IDataTypeFactory> dataTypeFactoryClass() default IDataTypeFactory.class;

    /**
     * @return value which configures DatabaseConfig.PROPERTY_METADATA_HANDLER
     */
    Class<? extends IMetadataHandler> metaDataHandler() default IMetadataHandler.class;


    /**
     * @return implementations of {@link Replacer}, which are merged with default replacers
     */
    Class<? extends Replacer>[] replacers() default {};

    /**
     * Specifies the orthography/letter-case strategy if {@link #caseSensitiveTableNames()} is set to <code>false</code>
     * (case-insensitive mode). If {@link #caseSensitiveTableNames()}=<code>false</code>, then this property is ignored
     * and has no effect. Defaults to {@link Orthography#UPPERCASE}.<br>
     * This is a database-rider internal property and not related to dbunit.
     *
     * @since 1.2.0
     * @return The {@link Orthography} constant
     */
    Orthography caseInsensitiveStrategy() default Orthography.UPPERCASE;

    /**
     *
     * @return Enable or disable dataset merging. If enabled then datasets declared at class/test level AND method will be merged into one dataset to seed database.
     * Note that only array properties of @DataSet annotation like 'value' and 'executeStatementsBefore' will be merged.
     */
    boolean mergeDataSets() default false;

    /**
     * When dataset merging strategy is set to CLASS then we first load class level datasets and later method level datasets
     * Default is method
     */
    DataSetMergingStrategy mergingStrategy() default DataSetMergingStrategy.METHOD;

    /**
     * If enabled, when loading datasets, reads in the whole XML into a buffer and dynamically adds new columns as they appear.
     * This way, it's not necessary to define all possible columns on the first line.
     *
     * @return Enables or disables column sensing. Defaults to false.
     */
    boolean columnSensing() default false;

    /**
     * Allow to call INSERT/UPDATE with empty strings ('').
     *
     * @return value which configures DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS. Defaults to false.
     */
    boolean allowEmptyFields() default false;

    /**
     * @return value which configures DatabaseConfig.PROPERTY_FETCH_SIZE. Defaults to 100
     */
    int fetchSize() default 100;

    /**
     * @return value which configures DatabaseConfig.PROPERTY_BATCH_SIZE. Defaults to 100
     */
    int batchSize() default 100;

    /**
     * Allows schema, table and column names escaping. The property value is an escape pattern where the ? is replaced
     * by the name. For example, the pattern "[?]" is expanded as "[MY_TABLE]" for a table named "MY_TABLE". The most
     * common escape pattern is "\"?\"" which surrounds the table name with quotes (for the above example it would
     * result in "\"MY_TABLE\""). As a fallback if no questionmark is in the given String and its length is one it is
     * used to surround the table name on the left and right side. For example the escape pattern "\"" will have the
     * same effect as the escape pattern "\"?\"".
     *
     * @return value which configures DatabaseConfig.PROPERTY_ESCAPE_PATTERN. Defaults to none
     */
    String escapePattern() default "";

    /**
     * @since 1.17.0
     * @return value which configures DatabaseConfig.PROPERTY_TABLE_TYPE (http://www.dbunit.org/properties/tableType)
     */
    String[] tableType() default {"TABLE"};

    /**
     * @since 1.21.0
     * @return list of table names to disable primary key check while creating the dataset.
     */
    String[] disablePKCheckFor() default {};

    /**
     * @since 0.15.0
     * @return driver class name. Used for declarative connections. Don't needed for drivers that implement jdbc 4.
     */
    String driver() default "";

    /**
     * @since 0.15.0
     * @return Database url. Used for declarative connections.
     */
    String url() default "";

    /**
     * @since 0.15.0
     * @return Username owner of database connection. Used for declarative connection.
     */
    String user() default "";

    /**
     * @since 0.15.0
     * @return Password of owner of database connection. Used for declarative connection.
     */
    String password() default "";

    /**
     * @since 1.11.0
     * @return Schema for the database connection.
     */
    String schema() default "";

}
