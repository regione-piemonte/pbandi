/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Subscription } from "rxjs";

const isFunction = (fn) => typeof fn === 'function';

const doUnsubscribe = (subscription) => {
    subscription && (subscription instanceof Subscription) && subscription.unsubscribe();
}

const doUnsubscribeIfArray = (subscriptionsArray) => {
    Array.isArray(subscriptionsArray) && subscriptionsArray.forEach(doUnsubscribe);
}

const doUnsubscribeIfObject = (subscriptionsObject) => {
    for (let key in subscriptionsObject) {
        doUnsubscribe(subscriptionsObject[key]);
    }
}


/**
 * Decorator that unsubscribe properties of type Subscription in current object.
 * Mode aviable:
 *  - auto:     cycle all properties in current object and do 'unsubscribe' if object's type is Subscription (and is not in black list)
 *  - array:    cycle all objects in array of Subscription (specified by 'arrayName') and do 'unsubscribe'
 *  - object:   cycle all properties in object that contains Subscriptions (specified by 'objectName') and do 'unsubscribe'
 * 
 * @param auto enable auto mode
 * @param blackList in auto mode, ignore object in blackList. 
 * @param objectName specify name of object that contains Subscriptions
 * @param arrayName specify name of array that contains Subscriptions
 */
export function AutoUnsubscribe({ auto = true, blackList = [], arrayName = '', objectName = '' } = {}) {

    return function (constructor: Function) {
        const original = constructor.prototype.ngOnDestroy;

        constructor.prototype.ngOnDestroy = function () {
            // array mode
            if (arrayName) {
                doUnsubscribeIfArray(this[arrayName]);
            }
            // object mode
            if (objectName) {
                doUnsubscribeIfObject(this[objectName])
            }
            // auto mode
            if (auto && !objectName && !arrayName) {
                for (let propName in this) {
                    if (blackList.includes(propName)) continue;
                    doUnsubscribe(this[propName]);
                }
            }

            isFunction(original) && original.apply(this, arguments);
        };
    }
}