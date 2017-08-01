package com.epam;

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

    public static void main(String[] args) {
        String pathSh = "C:\\Users\\Yayheniy_Lepkovich\\JavaProjects\\lyty\\lyty-corporate\\runShell.bat";
        try {
            Iterable<RevCommit> commitsSSH = getCommitsSSH();
            ArrayList<RevCommit> revCommits = new ArrayList<>();
            commitsSSH.iterator().forEachRemaining(revCommits::add);
            Collections.reverse(revCommits);
            for(RevCommit commit : revCommits) {
                checkoutCommit(commit.getName());
//                Process process = new ProcessBuilder("sh", pathSh).start();
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    private static Iterable<RevCommit> getCommitsSSH() throws IOException, GitAPIException {
        Repository repository = new FileRepository("C:\\Users\\Yayheniy_Lepkovich\\JavaProjects\\lyty\\lyty-corporate\\.git");
        Git git = new Git(repository);
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("lyty-corporate"))
                .call();
        return revCommits;
    }

    private static void checkoutCommit(String commitSSH) throws IOException, GitAPIException {
        Repository repository = new FileRepository("C:\\Users\\Yayheniy_Lepkovich\\JavaProjects\\lyty\\lyty-corporate\\.git");
        Git git = new Git(repository);
        git.checkout().setName(commitSSH).call();
    }
}
