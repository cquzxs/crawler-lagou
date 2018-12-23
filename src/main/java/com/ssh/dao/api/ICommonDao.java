package com.ssh.dao.api;

import org.hibernate.Session;

public interface ICommonDao {
    /**
     * 获取session
     * @return
     */
    Session getSession();
}
