/**
 * This file is part of topicmodeling.concurrent.
 *
 * topicmodeling.concurrent is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * topicmodeling.concurrent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with topicmodeling.concurrent.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dice_research.topicmodeling.concurrent.reporter;

import java.util.concurrent.Semaphore;

import org.dice_research.topicmodeling.concurrent.overseers.Overseer;
import org.dice_research.topicmodeling.concurrent.overseers.simple.SimpleOverseer;
import org.dice_research.topicmodeling.concurrent.tasks.SleepingTask;
import org.dice_research.topicmodeling.concurrent.tasks.Task;
import org.dice_research.topicmodeling.concurrent.tasks.TaskObserver;
import org.junit.Assert;
import org.junit.Test;

public class WaitingThreadInterrupterTest implements TaskObserver {

	private Semaphore stoppedTasks = new Semaphore(0);

	@Test(timeout = 3000)
	public void testTaskShouldBeInterrupted() {
	    stoppedTasks = new Semaphore(0);
		long MAX_WAITING_TIME = 1000;
		long TASK_SLEEP_DURATION = 5 * MAX_WAITING_TIME;

		Overseer overseer = new SimpleOverseer();
		overseer.addObserver(this);
		new WaitingThreadInterrupter(overseer, MAX_WAITING_TIME, (long) MAX_WAITING_TIME / 10);

		overseer.startTask(new SleepingTask("Sleep", TASK_SLEEP_DURATION));
		try {
			// wait for the task to be interrupted
			stoppedTasks.acquire();
		} catch (InterruptedException e) {
		}
	}

    @Test(timeout = 3000)
	public void testTaskShouldNotBeInterrupted() {
        stoppedTasks = new Semaphore(0);
		long MAX_WAITING_TIME = 1000;
		long TASK_SLEEP_DURATION = 100;

		Overseer overseer = new SimpleOverseer();
		overseer.addObserver(this);
		new WaitingThreadInterrupter(overseer, MAX_WAITING_TIME, (long) MAX_WAITING_TIME / 10);

		long timeNeeded = System.currentTimeMillis();
		overseer.startTask(new SleepingTask("Sleep", TASK_SLEEP_DURATION));
		try {
			// wait for the task to finish
			stoppedTasks.acquire();
		} catch (InterruptedException e) {
		}
		timeNeeded = System.currentTimeMillis() - timeNeeded;
		Assert.assertTrue(timeNeeded >= TASK_SLEEP_DURATION);
		Assert.assertTrue(timeNeeded < MAX_WAITING_TIME);
	}

	@Override
	public void reportTaskFinished(Task task) {
		stoppedTasks.release();
	}

	@Override
	public void reportTaskThrowedException(Task task, Throwable t) {
		stoppedTasks.release();
	}
}
