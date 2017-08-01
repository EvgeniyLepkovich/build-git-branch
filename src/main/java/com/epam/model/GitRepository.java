package com.epam.model;

import com.epam.util.CastUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GitRepository {
    private Repository repository;
    private Git git;

    private CastUtil<RevCommit> castUtil = new CastUtil<>();

    private static final String USER_DIR = "user.dir";
    private static final String GIT_DIR = "\\.git";
    private static final String DEfAULT_BRANCH = "master";

    public GitRepository() throws IOException {
        initNewGitRepository();
    }

    public GitRepository(String repositoryPath) throws IOException {
        initNewGitRepository(repositoryPath);
    }

    //master by default
    public ArrayList<RevCommit> getCommitsSSH(String branchName, boolean isReverse) throws IOException, GitAPIException {
        Iterable<RevCommit> commits = git.log()
                .add(repository.resolve(branchName.isEmpty() ? DEfAULT_BRANCH : branchName))
                .call();
        ArrayList<RevCommit> revCommits = castUtil.castIterableToArrayList(commits);
        if (isReverse) {
            Collections.reverse(revCommits);
        }
        return revCommits;
    }

    public void checkout(String branchName) throws GitAPIException {
        git.checkout().setName(branchName).call();
    }

    public void initNewGitRepository() throws IOException {
        initGitRepository(null);
    }

    public void initNewGitRepository(String repositoryPath) throws IOException {
        initGitRepository(repositoryPath);
    }

    private void initGitRepository(String repositoryPath) throws IOException {
        if (repositoryPath == null || repositoryPath.isEmpty()) {
            repositoryPath = System.getProperty(USER_DIR) + GIT_DIR;
        }
        repository = new FileRepository(repositoryPath);
        git = new Git(repository);
    }
}
