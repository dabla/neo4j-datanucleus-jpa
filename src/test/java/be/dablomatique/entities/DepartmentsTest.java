/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.dablomatique.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author dablomatique
 */
public class DepartmentsTest {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("HR");
    private EntityManager em = null;
    private EntityTransaction tx = null;

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @After
    public void tearDown() {
        try {
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }

            em.close();
        }
    }

    @Test
    public void testCreateDepartments() {
        final Departments department = new Departments();
        department.setDepartmentName("Dablomatique");

        final List<Employees> employees = new ArrayList<Employees>();
        final Employees employee = new Employees();
        employee.setEmail("dablomatique@gmail.com");
        employee.setFirstName("David");
        employee.setHireDate(new Date());
        employee.setLastName("Blain");
        employee.setPhoneNumber("590.423.4569");
        employee.setSalary(Double.valueOf(10000));

        employees.add(employee);

        department.setEmployeesList(employees);

        em.persist(department);
    }

    @Test
    public void testSelectDepartments() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Departments> cq = cb.createQuery(Departments.class);
        final Root<Departments> root = cq.from(Departments.class);
        cq.select(root);
        final TypedQuery<Departments> tq = em.createQuery(cq);
        final List<Departments> departments = tq.getResultList();
        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        assertTrue(departments.size() == 1);
    }
    
    @Test
    public void testFindDepartments() {
        final Departments department = em.find(Departments.class, 1);
        assertNotNull(department);
    }
    
    @Test
    public void testRemoveDepartments() {
        em.remove(em.find(Departments.class, 1));
    }
}