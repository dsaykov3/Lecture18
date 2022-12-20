package com.progress.dao;

import com.progress.model.ContactGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "groups")
public interface ContactGroupDAOJPARepository extends JpaRepository<ContactGroup, Integer> {
}
