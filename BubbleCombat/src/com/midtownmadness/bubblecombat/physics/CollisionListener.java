package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.dynamics.contacts.Contact;

import com.midtownmadness.bubblecombat.game.GameObject;

public interface CollisionListener {
	void collision(GameObject gameObj1, GameObject gameObj2, Contact contact);
}
