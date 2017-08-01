package com.epam;

import com.epam.model.GitRepository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final String CURRENT_GIT_PATH = "C:\\Users\\Yayheniy_Lepkovich\\JavaProjects\\lyty\\lyty-corporate\\.git";
    private static final String CURRENT_GIT_BRANCH = "lyty-corporate";

    public static void main(String[] args) {
        String pathSh = "C:\\Users\\Yayheniy_Lepkovich\\JavaProjects\\lyty\\lyty-corporate\\runShell.bat";
        try {
            GitRepository gitRepository = new GitRepository(CURRENT_GIT_PATH);
            ArrayList<RevCommit> revCommits = gitRepository.getCommitsSSH(CURRENT_GIT_BRANCH, true);
            for(RevCommit commit : revCommits) {
                gitRepository.checkout(commit.getName());
                Process process = new ProcessBuilder(pathSh, commit.getName()).start();

                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String str = null;
                while ((str = stdInput.readLine()) != null) {
                    System.out.println(str);
                }

                String strError = null;
                while ((strError = stdError.readLine()) != null) {
                    System.out.println(strError);
                }
            }
            gitRepository.checkout(CURRENT_GIT_BRANCH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
