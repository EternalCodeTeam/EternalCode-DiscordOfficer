package com.eternalcode.discordapp.expierience;

import com.j256.ormlite.dao.Dao;

import java.util.concurrent.CompletableFuture;

public interface ExperienceRepository {
    CompletableFuture<Experience> findUser(long id);

    CompletableFuture<Dao.CreateOrUpdateStatus> saveUser(Experience user);

    CompletableFuture<Integer> deleteUser(Experience user);

    CompletableFuture<Integer> deleteUserById(long id);
}
