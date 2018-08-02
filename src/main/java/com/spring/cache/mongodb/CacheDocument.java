package com.spring.cache.mongodb;

import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * <p>
 * description
 * </p>
 *
 * @author wangliang
 * @since 2018/8/1
 */
public class CacheDocument {
    private Instant creationDate;
    @Id
    private String id;
    private String element;

    /**
     * Default constructor.
     */
    public CacheDocument() {
    }

    /**
     * Constructor.
     *
     * @param id     a identifier.
     * @param element a element.
     */
    public CacheDocument(String id, String element) {
        this(id, element, Instant.now());
    }

    /**
     * Constructor.
     *
     * @param id           a identifier.
     * @param element      a element.
     * @param creationDate a creation date.
     */
    public CacheDocument(String id, String element, Instant creationDate) {
        this.id = id;
        this.element = element;
        this.creationDate = creationDate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
